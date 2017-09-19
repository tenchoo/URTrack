package com.urt.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TariffPlan;
 
public interface TariffPlanMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(TariffPlan record);

    int insertSelective(TariffPlan record);

    TariffPlan selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(TariffPlan record);

    int updateByPrimaryKey(TariffPlan record);
    
    TariffPlan doQueryFirst(@Param("tariffPlanName")String tariffPlanName,@Param("tariffPlanStauts")int tariffPlanStauts);
}