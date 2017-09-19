package com.urt.interfaces.authority;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSsLoginLogDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.dto.LaoSsRoleNavigationDto;

/**
 * 功能描述：角色接口
 * @author zhaoxy9
 * @date 2016年6月22日
 */
public interface LaoSsRoleService {
	/**
	 * 功能描述：查找角色列表
	 * @author zhaoxy
	 * @date 2016年6月22日 下午3:04:10
	 * @param @param tfFSsRoleDto
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return 
	 * @return Map<String,Object>
	 */
	public Map<String, Object> selectRoleByPage(LaoSsRoleDto roleDto,
			int pageNo, int pageSize);
	
	/**
	 * 功能描述：查询系统角色集合
	 * @author zhangbt3
	 * @date 2016年6月24日 上午10:40:34
	 * @return
	 */
	public List<LaoSsRoleDto> selectAllSsRole();
	/**
	 * 功能描述：增加角色
	 * @author zhaoxy9
	 * @date 2016年6月22日 下午5:17:06
	 * @param @param tfFSsRoleDto 
	 * @return void
	 */
	public void saveRole(LaoSsRoleDto roleDto,String navigationIds);
	
	/**
	 * 功能描述：根据id查询角色
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:02:56
	 * @param @param id
	 * @param @return 
	 * @return TfFSsRoleDto
	 */
	public LaoSsRoleDto queryRoleById(Long id);

	/**
	 * 功能描述：查询角色对应的菜单
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午4:23:08
	 * @param @param roleId
	 * @param @return 
	 * @return List<TfFSsRoleNavigationDto>
	 */
	public List<LaoSsRoleNavigationDto> queryListByRoleId(Long roleId);
	/**
	 * 功能描述：修改角色
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午5:44:42
	 * @param @param tfFSsRoleDto
	 * @param @param navigationIds 
	 * @return void
	 */
	public void updateRole(LaoSsRoleDto roleDto, String navigationIds);
	
	public List<LaoSsRoleDto> queryRoleListByAccountId(Long id);
	
	public LaoSsRoleDto queryRoleByRoleName(String roleName);
	
	/**
	 * 记录登录日志
	 * @param dto
	 * @return
	 */
	public Integer addLoginLog(LaoSsLoginLogDto dto);
	
	/**
	 * 功能描述： 删除角色
	 *  
	 * @param roleId
	 */
	public void deleteRole(Long roleId);
	
	/**
	 * 查询低优先级的角色（包含本优先级）
	 * 
	 * @param priority 当前优先级，数字越小优先级越高
	 * @return
	 */
	public List<LaoSsRoleDto> selectLowPriorityRole(Long priority);

}
