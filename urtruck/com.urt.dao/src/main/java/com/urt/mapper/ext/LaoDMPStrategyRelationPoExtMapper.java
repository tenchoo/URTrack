package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPOperationPo;
import com.urt.po.LaoDMPStrategyPo;
import com.urt.po.LaoDMPStrategyRelationPo;


public interface LaoDMPStrategyRelationPoExtMapper {

	List<LaoDMPStrategyRelationPo> selectByCustId(@Param("custId")long custId);

	void deleteBySchemeId(@Param("schemeId")Long schemeId);
}