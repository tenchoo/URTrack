package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoAlmRuleTypePo;

public interface LaoAlmRuleTypePoExtMapper {
	public List<Map<String, Object>> getLevel1();

	public List<Map<String, Object>> getLevel2(Integer pid);

	public LaoAlmRuleTypePo getRuleTypeById(Long ruleTypeId);
}
