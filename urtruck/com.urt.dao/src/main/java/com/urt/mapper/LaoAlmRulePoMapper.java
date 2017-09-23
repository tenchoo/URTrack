package com.urt.mapper;

import com.urt.po.LaoAlmRulePo;

public interface LaoAlmRulePoMapper {
    int deleteByPrimaryKey(Long ruleId);

    int insert(LaoAlmRulePo record);

    int insertSelective(LaoAlmRulePo record);

    LaoAlmRulePo selectByPrimaryKey(Long ruleId);

    int updateByPrimaryKeySelective(LaoAlmRulePo record);

    int updateByPrimaryKey(LaoAlmRulePo record);
}