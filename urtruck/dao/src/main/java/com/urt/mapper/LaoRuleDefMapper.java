package com.urt.mapper;

import com.urt.po.LaoRuleDef;

public interface LaoRuleDefMapper {
    int deleteByPrimaryKey(Long ruleId);

    int insert(LaoRuleDef record);

    int insertSelective(LaoRuleDef record);

    LaoRuleDef selectByPrimaryKey(Long ruleId);

    int updateByPrimaryKeySelective(LaoRuleDef record);

    int updateByPrimaryKey(LaoRuleDef record);
}