package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPStrategyPo;


public interface LaoDMPSchemeStrategyPoExtMapper {
	List<LaoDMPStrategyPo> selectBySchemeId(@Param("schemeId")Long schemeId);
}