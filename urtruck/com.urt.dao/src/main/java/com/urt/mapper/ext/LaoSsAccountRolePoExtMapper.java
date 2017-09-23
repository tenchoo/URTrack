package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoSsAccountRolePo;

/**
* 类说明：系统用户角色中间表扩展接口
* @author cuichao
* @date 2016年6月20日 下午3:29:24
*/
public interface LaoSsAccountRolePoExtMapper {

	/**
	 * 功能描述：根据用户id获取角色id集
	 * @author cuichao
	 * @date 2016年6月20日 下午3:38:04
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsUserRolePo>
	 */
	List<LaoSsAccountRolePo> queryListByUserId(Long acconutId);
	
	/**
	 * 功能描述：根据用户id和角色获取角色实体
	 * @author zhangbt3
	 * @date 2016年6月20日 下午4:50:14
	 * @param userId
	 * @return TfFSsUserRolePo
	 */
	LaoSsAccountRolePo selectByPrimaryKey(Long userId, Long roleId);

	/**
	 * 功能描述:根据角色标识查询用户角色列表
	 * @param roleId
	 * @return
	 */
	List<LaoSsAccountRolePo> queryListByRoleId(Long roleId);
}
