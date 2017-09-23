package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeOperatorPlan;

public interface TradeOperatorPlanMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("goodsId") Integer goodsId, @Param("goodsIndex") Short goodsIndex);

    int insert(TradeOperatorPlan record);

    int insertSelective(TradeOperatorPlan record);

    TradeOperatorPlan selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("goodsId") Integer goodsId, @Param("goodsIndex") Short goodsIndex);

    int updateByPrimaryKeySelective(TradeOperatorPlan record);

    int updateByPrimaryKey(TradeOperatorPlan record);
}