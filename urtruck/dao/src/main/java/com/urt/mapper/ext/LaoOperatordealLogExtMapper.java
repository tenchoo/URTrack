package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoOperatordealLog;

public interface LaoOperatordealLogExtMapper {
	
	List<LaoOperatordealLog> getFailOrder();
	
	LaoOperatordealLog getDealLogByRequest(@Param("requestId")String requestId);
	
	int updateFailNumByRequestId(@Param("requestId")String requestId);
	
}