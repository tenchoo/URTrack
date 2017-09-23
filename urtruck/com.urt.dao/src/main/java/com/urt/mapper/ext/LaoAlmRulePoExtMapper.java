package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoAlmRulePo;

public interface LaoAlmRulePoExtMapper {
	public List<Map<String, Object>> queryPage(Page<LaoAlmRulePo> page);
	
	public Integer deleteByPrimaryKey(Long ruleId);
	
	public List<LaoAlmRulePo> getAllRules();

	public List<LaoAlmRulePo> getRulesByCustId(@Param("channelcustId")Long channelcustId);
}