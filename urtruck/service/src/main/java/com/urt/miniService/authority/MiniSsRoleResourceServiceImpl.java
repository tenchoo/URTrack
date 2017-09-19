package com.urt.miniService.authority;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoRoleResourceDto;
import com.urt.mapper.TfFSsRoleResourcePoMapper;
import com.urt.po.TfFSsRoleNavigationPo;
import com.urt.po.TfFSsRoleResourcePo;

/**
 * 功能描述：角色资源中间表微服务
 * @author zhaoxy9
 * @date 2016年6月21日
 */
public class MiniSsRoleResourceServiceImpl {

	@Autowired
	private TfFSsRoleResourcePoMapper tfFSsRoleResourcePoMapper;
	/**
	 * 功能描述：角色资源新增微服务
	 * @date 2016年6月20日 下午7:00:43
	 * @param dto
	 * @return int
	 */
	public int saveSsRoleResource(LaoRoleResourceDto dto) {
		TfFSsRoleResourcePo po = (TfFSsRoleResourcePo)ConversionUtil.dto2po(dto, TfFSsRoleResourcePo.class);
		return tfFSsRoleResourcePoMapper.insertSelective(po);
	}
	
	/**
	 * 功能描述：删除角色资源
	 * @date 2016年6月20日  下午7:10:35
	 * @param roleId
	 * @param navigationId
	 * @return
	 */
	public int deleteSsRoleResource(Long roleId,Long resourceId){
		return tfFSsRoleResourcePoMapper.deleteByPrimaryKey(roleId,resourceId);
	}
}
