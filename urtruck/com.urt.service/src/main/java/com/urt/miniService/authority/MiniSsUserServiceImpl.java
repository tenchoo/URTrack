package com.urt.miniService.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoSsAccountPoMapper;
import com.urt.mapper.LaoSsAccountRolePoMapper;
import com.urt.mapper.LaoSsRolePoMapper;
import com.urt.mapper.TfFSsRolePoMapper;
import com.urt.mapper.TfFSsUserPoMapper;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.mapper.ext.LaoSsAccountRolePoExtMapper;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoSsAccountPo;
import com.urt.po.LaoSsAccountRolePo;
import com.urt.po.LaoSsRolePo;
import com.urt.po.TfFSsRolePo;
import com.urt.po.TfFSsUserPo;
import com.urt.po.TfFSsUserRolePo;

/**
 * 类说明：系统用户微服务
 * @author zhangbt3
 * @date 2016年6月20日 下午14:20:30
 */
@Service("miniUserService")
public class MiniSsUserServiceImpl {
	/*@Autowired
	private LaoSsAccountPoMapper laoSsAccountDao;*/
	@Autowired
	private LaoSsAccountPoExtMapper accountExtDao;
	@Autowired
	private LaoSsAccountPoMapper accountDao;
	@Autowired
	private LaoSsAccountRolePoExtMapper accountRoleExtDao;
	@Autowired
	private LaoSsRolePoMapper rolePoDao;
	@Autowired
	private LaoCustomerPoMapper custDao;
	/**
	 * 功能描述：系统用户新增微服务
	 * @author zhangbt3
	 * @date 2016年6月20日 下午2:27:43
	 * @param dto
	 * @return int
	 */
	public int saveUser(LaoSsAccountDto dto) {
		LaoSsAccountPo po = (LaoSsAccountPo)ConversionUtil.dto2po(dto, LaoSsAccountPo.class);
		return accountDao.insertSelective(po);
	}
	
	/**
	 * 功能描述：查询系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午2:28:20
	 * @param  userId
	 * @return TfFSsRoleDto
	 */
	public LaoSsAccountDto selectSsUserById(Long acconutId) {
		
		LaoSsAccountPo po = accountDao.selectByPrimaryKey(acconutId);
		LaoSsAccountDto dto = (LaoSsAccountDto) ConversionUtil.po2dto(po, LaoSsAccountDto.class);
		List<LaoSsAccountRolePo> UserRoleList = accountRoleExtDao.queryListByUserId(acconutId);
		List<LaoSsRoleDto> roles = new ArrayList<LaoSsRoleDto>();
		//根据用户角色中间表查询用户角色集合，不为空则添加到SsUserDto中的用户集合
		if(UserRoleList != null && UserRoleList.size() > 0){
			for (LaoSsAccountRolePo accountRolePo : UserRoleList) {
				LaoSsRolePo rolePo = rolePoDao.selectByPrimaryKey(accountRolePo.getRoleId());
				LaoSsRoleDto roleDto = (LaoSsRoleDto) ConversionUtil.po2dto(rolePo, LaoSsRoleDto.class);
				roles.add(roleDto);
			}
			dto.setRoles(roles);
		}
		return dto;
	}
	
	/**
	 * 功能描述：更新系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午2:38:11
	 * @param dto
	 * @return
	 */
	public int updateSsUser(LaoSsAccountDto dto){
		LaoSsAccountPo record = (LaoSsAccountPo) ConversionUtil.dto2po(dto, LaoSsAccountPo.class);
		return accountDao.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 功能描述：删除系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午2:29:01
	 * @param userId
	 * @return
	 */
	public int deleteSsRole(Long userId){
		return accountDao.deleteByPrimaryKey(userId);
	}

	/**
	 * 功能描述：分页查询联系人列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:48:13
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectTfFSsUserByPage(LaoSsAccountDto dto, int pageNo, int pageSize) {
		Page<LaoSsAccountPo> userPage = new Page<LaoSsAccountPo>();
		userPage.setPageNo(pageNo);
		userPage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoSsAccountPo) ConversionUtil.dto2po(dto, LaoSsAccountPo.class));
		// 查询当前客户的下级客户列表
		if (dto.getCustId() != null) {
			List<LaoCustomerPo> custList = custDao.selectChildCustList(dto.getCustId());
			StringBuilder sb = new StringBuilder();
			for (LaoCustomerPo po : custList){
				sb.append("'").append(po.getCustId()).append("'").append(",");
			}
			String custIdList = sb.toString().substring(0, sb.toString().length() - 1);
			if (StringUtils.isNotEmpty(custIdList)){
				params.put("custIdList", custIdList);
			}
		}
		userPage.setParams(params);
		
		List<LaoSsAccountPo> ssUserList = accountExtDao.queryUserByPage(userPage);
		List<LaoSsAccountDto> ssUserDtoList = ConversionUtil.poList2dtoList(ssUserList, LaoSsAccountDto.class);
		List<LaoSsAccountDto> SsUserList = new ArrayList<LaoSsAccountDto>();
		List<LaoSsRoleDto> roles = new ArrayList<LaoSsRoleDto>();
		//根据用户角色中间表查询角色，并添加到SsUserDto中的用户集合
		for (LaoSsAccountDto userDto : ssUserDtoList) {
			List<LaoSsAccountRolePo> UserRoleList = accountRoleExtDao.queryListByUserId(userDto.getAcconutId());
			if(UserRoleList != null && UserRoleList.size() > 0){
				for (LaoSsAccountRolePo userRolePo : UserRoleList) {
					LaoSsRolePo rolePo = rolePoDao.selectByPrimaryKey(userRolePo.getRoleId());
					LaoSsRoleDto roleDto = (LaoSsRoleDto) ConversionUtil.po2dto(rolePo, LaoSsRoleDto.class);
					roles.add(roleDto);
				}
			}
			userDto.setRoles(roles);
			SsUserList.add(userDto);
			//循环一次，需要将角色集合清空
			roles = new ArrayList<LaoSsRoleDto>();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", SsUserList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", userPage.getTotalRecord());//总记录数 
		return resultMap;
	}
	
	
	/**
	 * 功能描述：根据用户实体查询用户
	 * @author zhangbt3
	 * @date 2016年6月27日 下午7:50:40
	 * @param @param dto
	 * @param @return 
	 * @return List<TfFSsUserDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsAccountDto> queryTfFSsUsersByModel(LaoSsAccountDto dto){
		LaoSsAccountPo user = (LaoSsAccountPo) ConversionUtil.po2dto(dto, LaoSsAccountPo.class);
		List<LaoSsAccountPo> list = accountExtDao.queryUsersByModel(user);
		return ConversionUtil.poList2dtoList(list, LaoSsAccountDto.class);
	}
	/**
	 * 功能描述：根据用户名查询用户
	 * @author cuichao
	 * @date 2016年6月20日 下午7:59:38
	 * @param @param loginName
	 * @param @return 
	 * @return TfFSsUserDto
	 */
	public LaoSsAccountDto getByLoginName(String loginName){
		LaoSsAccountPo po = accountExtDao.queryByLoginName(loginName);
		if(po != null){
			LaoSsAccountDto dto = (LaoSsAccountDto) ConversionUtil.po2dto(po, LaoSsAccountDto.class);
			return dto;
		}
		return null;
	}
	
}
