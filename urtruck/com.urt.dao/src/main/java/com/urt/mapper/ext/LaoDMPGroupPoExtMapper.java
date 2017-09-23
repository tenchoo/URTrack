package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoDMPGroupPo;


public interface LaoDMPGroupPoExtMapper {
	List<HashMap<String,Object>> queryPage(Page<LaoDMPGroupPo> page);
	Integer delDeviceManage(Long deviceManageId);
	List<LaoDMPGroupPo> getGroupsByCustId(@Param("custId")Long custId);

}