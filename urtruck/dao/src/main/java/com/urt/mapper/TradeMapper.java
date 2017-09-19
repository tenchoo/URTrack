package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.Trade;

public interface TradeMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);
}