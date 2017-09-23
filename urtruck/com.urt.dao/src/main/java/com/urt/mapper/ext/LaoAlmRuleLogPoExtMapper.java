package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoAlmRuleLogPo;

public interface LaoAlmRuleLogPoExtMapper {

	List<Map<String, Object>> selectAlmInfo(@Param("custId")long custId);

	List<Map<String, Object>> selectAlmInfoAll(@Param("custId")long custId);

	List<Map<String, Object>> queryPage(Page<LaoAlmRuleLogPo> page);

	List<Map<String, Object>> queryCardInfo(@Param("almId")String almId);

	List<Map<String, Object>> queryPoolCardInfo(@Param("almId")String almId);
}