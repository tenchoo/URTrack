package com.urt.mapper;

import com.urt.po.OperatorPlan;

public interface OperatorPlanMapper {
    int deleteByPrimaryKey(Integer planId);

    int insert(OperatorPlan record);

    int insertSelective(OperatorPlan record);

    OperatorPlan selectByPrimaryKey(Integer planId);

    int updateByPrimaryKeySelective(OperatorPlan record);

    int updateByPrimaryKey(OperatorPlan record);
}