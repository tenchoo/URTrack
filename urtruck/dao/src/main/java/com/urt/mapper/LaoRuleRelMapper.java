package com.urt.mapper;

import com.urt.po.LaoRuleRel;

public interface LaoRuleRelMapper {
    int deleteByPrimaryKey(Long rulegroupId);

    int insert(LaoRuleRel record);

    int insertSelective(LaoRuleRel record);

    LaoRuleRel selectByPrimaryKey(Long rulegroupId);

    int updateByPrimaryKeySelective(LaoRuleRel record);

    int updateByPrimaryKey(LaoRuleRel record);
}