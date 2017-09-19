package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsRoleNavigationPo;
import com.urt.po.TfFSsRoleNavigationPo;

public interface LaoSsRoleNavigationPoExtMapper {

	/**
	 * 功能描述：根据角色Id获取角色菜单中间实体集合
	 * @author zhangbt3
	 * @date 2016年6月23日 下午3:22:04
	 * @param @param roleId
	 * @param @return 
	 * @return List<TfFSsRoleNavigationPo>
	 */
	List<LaoSsRoleNavigationPo> queryListByRoleId(@Param("roleId")Long roleId);
	/**
	 * 
	 * 功能描述：根据角色删除角色菜单表
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午5:59:02
	 * @param @param roleId 
	 * @return void
	 */
	public void deleteRoleNavigationByRoleId(Long roleId);
}
