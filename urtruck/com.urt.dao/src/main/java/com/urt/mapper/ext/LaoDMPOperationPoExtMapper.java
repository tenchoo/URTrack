package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPOperationPo;

public interface LaoDMPOperationPoExtMapper {

	List<LaoDMPOperationPo> getOperations();

	LaoDMPOperationPo selectById(@Param("operationId")Long operationId);

	List<LaoDMPOperationPo> getOperationsBystategy(@Param("id")Long id, @Param("strategyId")Long strategyId);
   
}