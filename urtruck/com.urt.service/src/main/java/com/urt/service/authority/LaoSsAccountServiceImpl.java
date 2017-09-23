/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.service.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.urt.cache.RedisCacheUtil;
import com.urt.cache.RedisKey;
import com.urt.cache.UserCache;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.JsonUtil;
import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsAccountRoleDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.dto.LaoStaffDto;
import com.urt.dto.UserDto;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.mapper.LaoAccountRelPoMapper;
import com.urt.mapper.LaoSsAccessLogPoMapper;
import com.urt.mapper.LaoSsAccountPoMapper;
import com.urt.mapper.UserDao;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.mapper.ext.LaoStaffPoExtMapper;
import com.urt.mapper.ext.SsUserPoExtMapper;
import com.urt.miniService.authority.MiniSsRoleServiceImpl;
import com.urt.miniService.authority.MiniSsUserRoleServiceImpl;
import com.urt.miniService.authority.MiniSsUserServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.modules.security.utils.Digests;
import com.urt.po.LaoAccountRelPo;
import com.urt.po.LaoSsAccessLogPo;
import com.urt.po.LaoSsAccountPo;
import com.urt.po.LaoStaffPo;
import com.urt.po.User;
import com.urt.service.ServiceException;
import com.urt.utils.Clock;
import com.urt.utils.Encodes;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Service("laoSsAccountService")
@Transactional(propagation = Propagation.REQUIRED)
public class LaoSsAccountServiceImpl implements LaoSsAccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	/*private static final Logger LOGGER = LoggerFactory.getLogger(TfFSsUserServiceImpl.class);*/
	private static Logger logger = LoggerFactory
			.getLogger(LaoSsAccountServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserCache userCache;
	@Autowired
	private SsUserPoExtMapper ssUserPoExtMapper;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	@Autowired
	private TagServiceImpl TagService;
	@Autowired
	private LaoSsAccountPoMapper laoSsAccountDao;
	@Autowired
	private MiniSsUserServiceImpl userService;
	@Autowired
	private MiniSsRoleServiceImpl roleService;
	@Autowired
	private MiniSsUserRoleServiceImpl userRoleService;
	@Autowired
	private LaoSsAccountPoExtMapper accountExtDao;
	@Autowired
	LaoSsAccountPoMapper accountDao;
	
	@Autowired
	private LaoAccountRelService relService;
	
	@Autowired
	LaoCustomerService customerService;
	
	@Autowired 
	LaoAccountRelPoMapper relDao;
	
	@Autowired 
	LaoSsAccessLogPoMapper accessLogDao;
	
	@Autowired 
	LaoStaffPoExtMapper staffExtDao;


	// private TaskDao taskDao;
	private Clock clock = Clock.DEFAULT;

	public List<UserDto> getAllUserDto() {
		Map<String, Object> parameter = Maps.newHashMap();
		return ConversionUtil.poList2dtoList(userDao.search(parameter),
				UserDto.class);
	}

	public UserDto getUserDto(Long id) {
		UserDto userDto = userCache.getUser(id);
		if (userDto == null || userDto.getId() == null) {
			userDto = (UserDto) ConversionUtil.po2dto(userDao.get(id),
					UserDto.class);
			userCache.save(userDto);
		}
		return userDto;
	}

	public LaoSsAccountDto getUserByLoginName(String loginName) {
		LaoSsAccountDto dto=new LaoSsAccountDto();
		if(accountExtDao.queryByLoginName(loginName)!=null){
			BeanMapper.copy(accountExtDao.queryByLoginName(loginName), dto);
			return dto;
		}else{
			return null;
		}
		
		
	}

	public void registerUserDto(UserDto userDto) {
		entryptPassword(userDto);

		userDto.setRoles("user");
		userDto.setRegisterDate(clock.getCurrentDate());

		userDao.save((User) ConversionUtil.dto2po(userDto, User.class));
	}

	public void updateUserDto(UserDto userDto) {
		if (StringUtils.isNotBlank(userDto.getPlainPassword())) {
			entryptPassword(userDto);
		}
		userDao.save((User) ConversionUtil.dto2po(userDto, User.class));
	}

	public void deleteUserDto(Long id) {
		if (isSupervisor(id)) {
			// logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		// taskDao.deleteByUserId(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	public boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	/*
	 * private String getCurrentUserName() { ShiroUser user = (ShiroUser)
	 * SecurityUtils.getSubject().getPrincipal(); return user.loginName; }
	 */

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(UserDto userDto) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		userDto.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(userDto.getPlainPassword()
				.getBytes(), salt, HASH_INTERATIONS);
		userDto.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * @Autowired public void setTaskDao(TaskDao taskDao) { this.taskDao =
	 * taskDao; }
	 */
	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/**
	 * 功能描述：根据用户Id获取客户Id
	 * 
	 * @author wangfu
	 * @date 2016年5月23日 下午5:30:15
	 * @param @param id
	 * @param @return
	 */
	@Override
	public Long selectById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);
		map = ssUserPoExtMapper.selectById(map);
		return Long.parseLong(map.get("custId").toString());
	}

	public List<Map> redisMap(String custState) {
		List<Map> tolist = new ArrayList<>();
		try {
			// 获取枚举项
			String trans = "";
			trans = redisCacheUtil.getStr(RedisKey.SELECT_TYPE_CODE, custState);
			if (trans != null && !trans.isEmpty()) {
				// 转化为集合
				tolist = JsonUtil.fromJson(trans, new TypeToken<List<Map>>() {
				}.getType());
			} else {
				String sql = "SELECT * FROM CODE_LIST T WHERE  T.SYS_CODE = '1' AND  T.TYPE_CODE = '"
						+ custState
						+ "'AND T.REMOVE_TAG = '0' ORDER BY T.SEQ_NO";
				tolist = TagService.getOptions(sql);
				redisCacheUtil.save(RedisKey.SELECT_TYPE_CODE, custState,
						JsonUtil.toJson(tolist));
			}
		} catch (Exception e) {
			logger.equals("获取枚举项数据失败：" + e);
		}
		return tolist;
	}

	@Override
	public LaoSsAccountDto getUserByRelared(String id) {
		// TODO Auto-generated method stub
		LaoSsAccountPo po=accountExtDao.getUserByRelared(id);
		if(po!=null){
			return userService.selectSsUserById(po.getAcconutId());
		}
		return null;
	}
	/**
	 * 功能描述：根据userId查询系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:32:13
	 * @param userId
	 * @return
	 */
	@Override
	public LaoSsAccountDto selectUserById(Long userId) {
		return userService.selectSsUserById(userId);
	}

	/**
	 * 功能描述：根据条件分页查询系统用户列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:33:21
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> selectUserByPage(LaoSsAccountDto dto, int pageNo, int pageSize) {
		Map<String, Object> resultMap = userService.selectTfFSsUserByPage(dto,pageNo,pageSize);
		return resultMap;
	}

	/**
	 * 功能描述：新增系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:11:30
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("finally")
	@Override
	public int saveUser(LaoSsAccountDto dto) {
		//生成用户Id
		Long userId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		int result = -1;
		dto.setAcconutId(userId);
		dto.setStatus("0");//用户状态先默认正常
		dto.setCreateDate(new Date());//用户的创建时间
		if(dto.getPlainPassword()==null){
			dto.setPlainPassword(dto.getPassword());
		}
		entryptPassword(dto);
		
		//获取前台传过来的role集合
		List<LaoSsRoleDto> roles = dto.getRoles();
		try {
			if(CollectionUtils.isNotEmpty(roles)){
				//插入用户角色中间表
				for (LaoSsRoleDto role : roles) {
					LaoSsAccountRoleDto userRoleDto= new LaoSsAccountRoleDto();
					userRoleDto.setRoleId(role.getRoleId());
					userRoleDto.setUserId(userId);
					userRoleService.insertTfFSsUserRole(userRoleDto);
				}
			}else{
				LaoSsAccountRoleDto userRoleDto= new LaoSsAccountRoleDto();
				userRoleDto.setRoleId(4l);
				userRoleDto.setUserId(userId);
				userRoleService.insertTfFSsUserRole(userRoleDto);
			}
			result = userService.saveUser(dto);
			if(result!=-1){
				if(dto.getRelatedId()!=null && dto.getRelatedId().trim().length()>0){
					Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
					LaoAccountRelPo po=new LaoAccountRelPo();
					po.setAccountId(userId);
					po.setRecvTime(new Date());
					po.setRelAccount(dto.getRelatedId());
					po.setRelId(id);
					po.setRelType("1000");
					relDao.insert(po);
				}
				
			}
		} catch (Exception e) {
			logger.debug("新增系统用户失败！");
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			//e.printStackTrace();
			return result;
		}
	}

	/**
	 * 功能描述：根据用户实体查询用户
	 * @author zhangbt3
	 * @date 2016年6月27日 下午8:00:21
	 * @param @param record
	 * @param @return 
	 * @return List<TfFSsUserDto>
	 */
	public List<LaoSsAccountDto> selectTfFSsUsersByModel(LaoSsAccountDto record) {
		return userService.queryTfFSsUsersByModel(record);
	}
	
	/**
	 * 功能描述：更新系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:12:11
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("finally")
	@Override
	public int updateUser(LaoSsAccountDto dto) {
		int result = -1;
		List<LaoSsAccountRoleDto> list = userRoleService.getListByUserId(dto.getAcconutId());
		//存在关联关系，则执行删除操作
		if(CollectionUtils.isNotEmpty(list)){
			userRoleService.deleteSsUserRole(dto.getAcconutId());
		}
		try {
			//获取前台传过来的role集合
			List<LaoSsRoleDto> roles = dto.getRoles();
			if(CollectionUtils.isNotEmpty(roles)){
				for (LaoSsRoleDto role : roles) {
					LaoSsAccountRoleDto userRoleDto= new LaoSsAccountRoleDto();
					userRoleDto.setRoleId(role.getRoleId());
					userRoleDto.setUserId(dto.getAcconutId());
					userRoleService.insertTfFSsUserRole(userRoleDto);
				}
			}
			result = userService.updateSsUser(dto);
		} catch (Exception e) {
			logger.debug("更新系统用户失败！");
			throw new RuntimeException();
		}finally{
			return result;
		}
	}
	
	/**
	 * 功能描述：重置密码（管理员使用）
	 * @author zhangbt3
	 * @date 2016年6月24日 下午2:44:54
	 * @param userId
	 * @return
	 */
	@Override
	public int resetPassword(Long userId) {
		LaoSsAccountDto dto = userService.selectSsUserById(userId);
		dto.setPlainPassword("111111");
		entryptPassword(dto);
		int result = -1;
		try {
			result = userService.updateSsUser(dto);
		} catch (Exception e) {
			logger.debug("重置密码失败！");
			throw new RuntimeException();
		}
		return result;
	}
	
	/**
	 * 功能描述：修改密码（用户个人使用）
	 * @author zhangbt3
	 * @date 2016年6月24日 下午2:44:54
	 * @param pb
	 * @return
	 */
	public int changePassword(LaoSsAccountDto dto,String oldpw){
		LaoSsAccountDto currentUserDto = userService.selectSsUserById(dto.getAcconutId());
		/*LaoSsAccountDto user=new LaoSsAccountDto();*/
		/*LaoSsAccountDto tfFSsUserDto = new LaoSsAccountDto();
		dto.setPassword(currentUserDto.getPlainPassword());*/
		//将传递过来的旧密码加密
		/*user.setPlainPassword(oldpw);*/
		/*byte[] salt = currentUserDto.getSalt().getBytes();
		entryptMatchPassword(user,salt);*/
		//加密后获取密码
	/*	String pw =  user.getPassword();*/
		//判断当前用户的填写旧密码是否与数据库存储的密码一致，一致则修改密码为新密码
		int result = -1;
		currentUserDto.setPlainPassword(dto.getPlainPassword());
		entryptPassword(currentUserDto);
		try {
			result = userService.updateSsUser(currentUserDto);
		} catch (Exception e) {
			logger.debug("修改密码失败！");
			throw new RuntimeException();
		}
		return result;
	}
	
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(LaoSsAccountDto laoSsAccountDto) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		laoSsAccountDto.setSalt(Encodes.encodeHex(salt));
		
		byte[] hashPassword = Digests.sha1(laoSsAccountDto.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		laoSsAccountDto.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 功能描述：匹配密码加密
	 * @param tfFSsUserDto
	 * @param salt
	 */
	private void entryptMatchPassword(LaoSsAccountDto tfFSsUserDto,byte[] salt) {
		byte[] hashPassword = Digests.sha1(tfFSsUserDto.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		tfFSsUserDto.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Override
	public List<LaoSsAccountDto> selectUsersByModel(LaoSsAccountDto record) {
		// TODO Auto-generated method stub
		/**/
		LaoSsAccountPo laoSsAccountPo=new LaoSsAccountPo();
		BeanMapper.copy(record, laoSsAccountPo);
		List<LaoSsAccountPo> pos=accountExtDao.queryUsersByModel(laoSsAccountPo);
		List<LaoSsAccountDto> dtos=new ArrayList<LaoSsAccountDto>();
		for(LaoSsAccountPo po:pos){
			LaoSsAccountDto dto=new LaoSsAccountDto();
			BeanMapper.copy(po, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public LaoSsAccountDto selectUserById(String relatedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateAccountCustId(Long accountId, Long custId) {
		// TODO Auto-generated method stub
		LaoSsAccountPo po=laoSsAccountDao.selectByPrimaryKey(accountId);
		po.setCustId(custId);
		return laoSsAccountDao.updateByPrimaryKey(po);
	}

	@Override
	public List<LaoSsAccountDto> queryByCustId(Long custId) {
		List<LaoSsAccountPo> pos=accountExtDao.queryByCustId(custId);
		List<LaoSsAccountDto> dtos=new ArrayList<LaoSsAccountDto>();
		for(LaoSsAccountPo po:pos){
			LaoSsAccountDto dto=new LaoSsAccountDto();
			BeanMapper.copy(po,dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public int saveCommonUser(LaoSsAccountDto dto) {
		//生成用户Id
				/*Long userId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));*/
				int result = -1;
			/*	dto.setAcconutId(userId);*/
				dto.setStatus("0");//用户状态先默认正常
				dto.setCreateDate(new Date());//用户的创建时间
				if(dto.getPlainPassword()==null){
					dto.setPlainPassword(dto.getPassword());
				}
				entryptPassword(dto);
				
				//获取前台传过来的role集合
				List<LaoSsRoleDto> roles = dto.getRoles();
				try {
					if(CollectionUtils.isNotEmpty(roles)){
						//插入用户角色中间表
						for (LaoSsRoleDto role : roles) {
							LaoSsAccountRoleDto userRoleDto= new LaoSsAccountRoleDto();
							userRoleDto.setRoleId(role.getRoleId());
							userRoleDto.setUserId(dto.getAcconutId());
							userRoleService.insertTfFSsUserRole(userRoleDto);
						}
					}else{
						LaoSsAccountRoleDto userRoleDto= new LaoSsAccountRoleDto();
						userRoleDto.setRoleId(3l);
						userRoleDto.setUserId(dto.getAcconutId());
						userRoleService.insertTfFSsUserRole(userRoleDto);
					}
					result = userService.saveUser(dto);
				} catch (Exception e) {
					logger.debug("新增系统用户失败！");
					throw new RuntimeException();
				}finally{
					return result;
				}
	}

	@Override
	public int updateCustId(LaoSsAccountDto dto, long custId) {
		// TODO Auto-generated method stub
		LaoSsAccountPo account=new LaoSsAccountPo();
		BeanMapper.copy(dto, account);
		account.setCustId(custId);
		return accountExtDao.updateCustId(account);
	}

	@Override
	public LaoSsAccountDto queryAccountById(Long id) {
		// TODO Auto-generated method stub
		 LaoSsAccountDto dto=null;
		 LaoSsAccountPo po = accountDao.selectByPrimaryKey(id);
		 if(po!=null){
			 dto=new LaoSsAccountDto();
			 BeanMapper.copy(po, dto);
		 }
		 return dto;
	}

	@Override
	public int updateAccount(LaoSsAccountDto dto) {
		int result = -1;
		result = userService.updateSsUser(dto);
		return result;
	}

	@Override
	public LaoSsAccountDto queryAccountByAcconutId(Long acconutId) {
		// TODO Auto-generated method stub
		LaoSsAccountPo selectByPrimaryKey = laoSsAccountDao.selectByPrimaryKey(acconutId);
		LaoSsAccountDto dto = new LaoSsAccountDto();
		BeanMapper.copy(selectByPrimaryKey, dto);
		return dto;
	}

	@Override
	public void bindingGla(String username, String password, String openId) {
		LaoAccountRelDto relDto = new LaoAccountRelDto();
		relDto.setRelType("1001");
		relDto.setRelAccount(openId);
		List<LaoAccountRelDto> rels = relService.queryRelByRelType(relDto);
		
		if(rels != null && rels.size() > 0){
			LaoAccountRelDto laoAccountRelDto = rels.get(0);
			Long accountId = laoAccountRelDto.getAccountId();
			LaoSsAccountDto laoSsAccountDto = queryAccountById(accountId);
			
			//如果openId 绑定的是当前用户，后面的逻辑不用；如果不是，解绑且
			if(laoSsAccountDto.getLoginName().equals(username)){
				return ;
			}else{
				for (LaoAccountRelDto accountRelDto : rels) {
					if(accountRelDto != null && accountRelDto.getRelId() != null){
						relDao.deleteByPrimaryKey(accountRelDto.getRelId());
					}
				}
				laoSsAccountDto = getUserByLoginName(username);
				Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
				relDto.setAccountId(laoSsAccountDto.getAcconutId());
				relDto.setRecvTime(new Date());
				relDto.setRelId(id);
				relService.save(relDto);
			}
		}else{
			LaoSsAccountDto laoSsAccountDto = getUserByLoginName(username);
			if(laoSsAccountDto != null){
				Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
				relDto.setAccountId(laoSsAccountDto.getAcconutId());
				relDto.setRecvTime(new Date());
				relDto.setRelId(id);
				relService.save(relDto);
			}
		}
	}

	@Override
	public int addAccessLog(Map<String,Object> mapDto) {
		// TODO Auto-generated method stub
		LaoSsAccessLogPo record=new LaoSsAccessLogPo();
		Long logId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		mapDto.put("logId", logId);
		record.setAccessTime((Date) mapDto.get("accessTime"));
		record.setUrl((String) mapDto.get("url"));
		record.setUserIp((String) mapDto.get("userIp"));
		record.setLoginId((String) mapDto.get("loginId"));
		record.setLoginName((String) mapDto.get("loginName"));
		record.setThirdPartyAccountId((String) mapDto.get("thirdPartyAccountId"));
		record.setThirdPartyAccountType((String) mapDto.get("thirdPartyAccountType"));
		record.setLogId((Long) mapDto.get("logId"));
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~封装后的数据" + record.toString());
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>当前访问的IP地址为：" + record.getUserIp().toString());
		return accessLogDao.insertSelective(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoStaffDto> queryStaffs() {
		List<LaoStaffPo> staffs = staffExtDao.queryStaffs();
		return ConversionUtil.poList2dtoList(staffs, LaoStaffDto.class);
	}


	/**
	 * 激活账号：
	 * 账号状态为1为冻结状态，调用该服务修改为正常状态（0为正常状态）
	 * @param accountId
	 */
	public void activeAccount(Long accountId) {
		changeAccountStatus(accountId, "0");
	}
	
	/**
	 * 冻结账号：
	 * 账号状态为0为冻结状态，调用该服务修改为正常状态（1为正常状态）
	 * @param accountId
	 */
	public void freezingAccount(Long accountId) {
		changeAccountStatus(accountId, "1");
	}
	
	/**
	 * 修改账号状态
	 * @param accountId
	 * @param status
	 */
	public void changeAccountStatus(Long accountId, String status) {
		LaoSsAccountPo accountPo = laoSsAccountDao.selectByPrimaryKey(accountId);
		if (accountPo != null) {
			accountPo.setStatus(status);
			laoSsAccountDao.updateByPrimaryKey(accountPo);
		}	
	}
}
