package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoSsRolePo;
import com.urt.po.TfFSsRolePo;
import com.urt.po.TfFSsUserPo;

/**
* 类说明：系统角色扩展接口
* @author cuichao
* @date 2016年6月20日 下午5:24:54
*/
public interface LaoSsRolePoExtMapper {
	/**
	 * 功能描述：分页查询角色列表
	 * @author zhaoxy9
	 * @date 2016年6月22日 上午11:47:27
	 * @param @param tffPage
	 * @param @return 
	 * @return List<TfFSsUserPo>
	 */
	public List<LaoSsRolePo> queryRoleByPage(Page<LaoSsRolePo> tffPage);
	
	/**
	 * 功能描述：查询角色集合
	 * @author zhangbt3
	 * @date 2016年6月24日 上午10:43:11
	 * @param @return 
	 * @return List<TfFSsUserPo>
	 */
	public List<LaoSsRolePo> queryRoleList();
	
	public List<LaoSsRolePo> queryRoleListByAccountId(Long id);
	
	public LaoSsRolePo queryRoleByRoleName(String roleName);
}
