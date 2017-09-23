package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPOperationPo;

public interface LaoDMPStrategyOperationPoExtMapper {

	List<LaoDMPOperationPo> getOperations(@Param("strategyId")Long strategyId);
}