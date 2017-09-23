package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoRuleRel;

public interface LaoRuleRelExtMapper {
	LaoRuleRel selectRelsByCustId(Long custId);
	int updateByCustId(LaoRuleRel record);
}