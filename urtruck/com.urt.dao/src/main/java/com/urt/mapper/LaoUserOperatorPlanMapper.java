package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserOperatorPlan;

public interface LaoUserOperatorPlanMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("planId") Integer planId, @Param("startDate") Date startDate, @Param("goodsIndex") Short goodsIndex);

    int insert(LaoUserOperatorPlan record);

    int insertSelective(LaoUserOperatorPlan record);

    LaoUserOperatorPlan selectByPrimaryKey(@Param("userId") Long userId, @Param("planId") Integer planId, @Param("startDate") Date startDate, @Param("goodsIndex") Short goodsIndex);

    int updateByPrimaryKeySelective(LaoUserOperatorPlan record);

    int updateByPrimaryKey(LaoUserOperatorPlan record);
}