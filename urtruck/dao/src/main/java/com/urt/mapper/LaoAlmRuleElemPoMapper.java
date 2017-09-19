package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoAlmRuleElemPo;

public interface LaoAlmRuleElemPoMapper {
    int deleteByPrimaryKey(@Param("ruleTypeId") Long ruleTypeId, @Param("elementId") Long elementId);

    int insert(LaoAlmRuleElemPo record);

    int insertSelective(LaoAlmRuleElemPo record);

    LaoAlmRuleElemPo selectByPrimaryKey(@Param("ruleTypeId") Long ruleTypeId, @Param("elementId") Long elementId);

    int updateByPrimaryKeySelective(LaoAlmRuleElemPo record);

    int updateByPrimaryKey(LaoAlmRuleElemPo record);
}