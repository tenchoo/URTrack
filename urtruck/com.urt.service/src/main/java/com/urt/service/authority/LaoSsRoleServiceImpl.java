package com.urt.service.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Exc.BusiExceptionLog;
import com.urt.Exc.ExceCode;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoSsAccountRoleDto;
import com.urt.dto.LaoSsLoginLogDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.dto.LaoSsRoleNavigationDto;
import com.urt.interfaces.authority.LaoSsRoleService;
import com.urt.mapper.LaoSsLoginLogPoMapper;
import com.urt.mapper.LaoSsRolePoMapper;
import com.urt.mapper.ext.LaoSsRolePoExtMapper;
import com.urt.miniService.authority.MiniSsRoleNavigationServiceImpl;
import com.urt.miniService.authority.MiniSsRoleServiceImpl;
import com.urt.miniService.authority.MiniSsUserRoleServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoSsLoginLogPo;
import com.urt.po.LaoSsRolePo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;



/**
 * 功能描述：角色实现类
 * @author zhaoxy9
 * @date 2016年6月22日
 */
@Service("laoSsRoleService")
@Transactional(propagation=Propagation.REQUIRED)
public class LaoSsRoleServiceImpl implements LaoSsRoleService{
	private static final Logger LOGGER = LoggerFactory.getLogger(LaoSsRoleServiceImpl.class);
	@Autowired
	private MiniSsRoleServiceImpl miniSsRoleServiceImpl;
	@Autowired
	private MiniSsRoleNavigationServiceImpl miniSsRoleNavigationServiceImpl;
	
	@Autowired
	private MiniSsUserRoleServiceImpl miniSsUserRoleServiceImpl;
	
	@Autowired 
	private LaoSsRolePoExtMapper dao;
	@Autowired
	private LaoSsLoginLogPoMapper loginLogDAO;
	
	@Autowired
	private LaoSsRolePoMapper roleDAO;
	
	/**
	 * 功能描述：根据id查询角色
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:03:26
	 * @param @param id
	 * @param @return
	 */
	public LaoSsRoleDto queryRoleById(Long id) {
		LaoSsRoleDto tfFSsRoleDto=miniSsRoleServiceImpl.selectSsRoleById(id);
		return tfFSsRoleDto;
	}
	/**
	 * 功能描述：角色分页实现方法
	 * @author zhaoxy9
	 * @date 2016年6月23日 上午11:34:39
	 * @param @param tfFSsRoleDto
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return
	 */
	@Override
	public Map<String, Object> selectRoleByPage(LaoSsRoleDto tfFSsRoleDto,
			int pageNo, int pageSize) {
		Map<String, Object> map=miniSsRoleServiceImpl.selectTfFRoleByPage(tfFSsRoleDto, pageNo, pageSize);
		return map;
	}
	
	/**
	 * 功能描述：查询系统角色集合
	 * @author zhangbt3
	 * @date 2016年6月23日 上午11:09:20
	 * @param userId
	 * @return TfFSsRoleDto
	 */
	public List<LaoSsRoleDto> selectAllSsRole() {
		return miniSsRoleServiceImpl.selectAllSsRoles();
	}
	
	/**
	 * 功能描述：增加角色
	 * @author zhaoxy9
	 * @date 2016年6月23日 上午11:35:26
	 * @param @param tfFSsRoleDto
	 */
	@Override
	public void saveRole(LaoSsRoleDto tfFSsRoleDto,String navigationIds) {
		Long roleId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.ROLE_ID));
		tfFSsRoleDto.setRoleId(roleId);
		try {
			miniSsRoleServiceImpl.saveSsRole(tfFSsRoleDto);
		} catch (Exception e) {
			LOGGER.error("******************增加角色信息错误*********************");
			throw new RuntimeException();
		}
		if (!"".equals(navigationIds)) {
		String navigationId[]=navigationIds.split(",");
		for (int i = 0; i < navigationId.length; i++) {
			LaoSsRoleNavigationDto dto=new LaoSsRoleNavigationDto();
			dto.setRoleId(roleId);
			dto.setNavigationId(Long.valueOf(navigationId[i]));
			try {
				miniSsRoleNavigationServiceImpl.saveSsRoleNavigation(dto);
			} catch (Exception e) {
				LOGGER.error("******************增加角色菜单中间表错误*********************");
				throw new RuntimeException();
			}
			
			}
		}
	}
	/**
	 * 功能描述：查询角色菜单中间表
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:48:27
	 * @param @param roleId
	 * @param @return 
	 * @return List<TfFSsRoleNavigationDto>
	 */
	public List<LaoSsRoleNavigationDto> queryListByRoleId(Long roleId) {
		 return miniSsRoleNavigationServiceImpl.queryListByRoleId(roleId);
	}
	@Override
	public void updateRole(LaoSsRoleDto tfFSsRoleDto, String navigationIds) {
		try {
			miniSsRoleServiceImpl.updateSsRole(tfFSsRoleDto);
		} catch (Exception e) {
			LOGGER.error("******************修改角色表错误*********************");
			throw new RuntimeException();
		}
		try {
			miniSsRoleNavigationServiceImpl.deleteTfFSsRoleNavigationByRoleId(tfFSsRoleDto.getRoleId());
		} catch (Exception e) {
			LOGGER.error("*****************删除角色菜单中间表错误*********************");
			throw new RuntimeException();
		}
		if (!"".equals(navigationIds)) {
			String navigationId[]=navigationIds.split(",");
			for (int i = 0; i < navigationId.length; i++) {
				LaoSsRoleNavigationDto dto=new LaoSsRoleNavigationDto();
				dto.setRoleId(tfFSsRoleDto.getRoleId());
				dto.setNavigationId(Long.valueOf(navigationId[i]));
				try {
					miniSsRoleNavigationServiceImpl.saveSsRoleNavigation(dto);
				} catch (Exception e) {
					LOGGER.error("******************增加角色菜单中间表错误*********************");
					throw new RuntimeException();
				}
				
				}
			}
		
	}
	@Override
	public List<LaoSsRoleDto> queryRoleListByAccountId(Long id) {
		// TODO Auto-generated method stub
		List<LaoSsRoleDto> list=new ArrayList<LaoSsRoleDto>();
		/*BeanMapper.copy(dao.queryRoleListByAccountId(id), list);*/
		List<LaoSsRolePo> rolePos = dao.queryRoleListByAccountId(id);
		LaoSsRoleDto dto=null;
		for(LaoSsRolePo po:rolePos){
			dto=new LaoSsRoleDto();
			BeanMapper.copy(po, dto);
			list.add(dto);
		}
		return list;
	}
	@Override
	public LaoSsRoleDto queryRoleByRoleName(String roleName) {
		// TODO Auto-generated method stub
		LaoSsRolePo po = dao.queryRoleByRoleName(roleName);
		LaoSsRoleDto dto=null;
		if(po!=null){
			dto=new LaoSsRoleDto();
			BeanMapper.copy(po, dto);
		}
		return dto;
	}
	@Override
	public Integer addLoginLog(LaoSsLoginLogDto dto) {
		// TODO Auto-generated method stub
		LaoSsLoginLogPo po=null;
		if(dto!=null){
			po=new LaoSsLoginLogPo();
			BeanMapper.copy(dto, po);
			return loginLogDAO.insertSelective(po);
		}
        
		return -1;
	}
	
	/**
	 * 功能描述： 删除角色，同时删除角色与菜单的关联表
	 *  
	 * @param roleId
	 */
	public void deleteRole(Long roleId) {
		//角色已经被用户使用，不能被删除
		List<LaoSsAccountRoleDto> list = miniSsUserRoleServiceImpl.queryListByRoleId(roleId);
		
		if (CollectionUtils.isNotEmpty(list)) {
			LOGGER.error("******************角色已经被使用不能被删除*********************");
			throw new BusiExceptionLog("该角色已经被使用，不能删除。", ExceCode.SYS_ROLE_USED);
		}
		
		// 删除角色
		try {
			miniSsRoleServiceImpl.deleteSsRole(roleId);
		}
		catch (Exception ex)
		{
			LOGGER.error("******************删除角色表错误*********************");
			throw new BusiExceptionLog("删除角色失败。", ex);
		}
		// 删除角色与菜单的关联信息
		try {
			miniSsRoleNavigationServiceImpl.deleteTfFSsRoleNavigationByRoleId(roleId);
		} catch (Exception ex) {
			LOGGER.error("*****************删除角色菜单错误*********************");
			throw new BusiExceptionLog("删除角色菜单失败。", ex);
		}
	}
	
	/**
	 * 查询低优先级的角色（包含本优先级）
	 * 
	 * @param priority 当前优先级，数字越小优先级越高
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsRoleDto> selectLowPriorityRole(Long priority) {
		try{
			
			List<LaoSsRolePo> rolePoList = roleDAO.selectLowPriorityRole(priority);
			List<LaoSsRoleDto> dtoList = ConversionUtil.poList2dtoList(rolePoList, LaoSsRoleDto.class);
			return dtoList;
		} catch (Exception ex) {
			LOGGER.error("*****************查询角色菜单错误*********************");
			throw new RuntimeException();
		}
	}
}
