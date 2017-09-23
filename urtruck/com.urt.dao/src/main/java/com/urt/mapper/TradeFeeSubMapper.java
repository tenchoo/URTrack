package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeFeeSub;

public interface TradeFeeSubMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("goodsId") Long goodsId);

    int insert(TradeFeeSub record);

    int insertSelective(TradeFeeSub record);

    TradeFeeSub selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("goodsId") Long goodsId);

    int updateByPrimaryKeySelective(TradeFeeSub record);

    int updateByPrimaryKey(TradeFeeSub record);
}