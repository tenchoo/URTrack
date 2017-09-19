package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPStrategyPo;

public interface LaoDMPStrategyPoExtMapper {

	List<LaoDMPStrategyPo> getStrategys();

	LaoDMPStrategyPo selectById(@Param("strategyId")Long strategyId);
	
	List<LaoDMPStrategyPo> getStrategis(@Param("id")Long id);
}