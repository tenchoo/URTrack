package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeDiscont;

public interface TradeDiscontMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("discntId") Integer discntId, @Param("startDate") Date startDate);

    int insert(TradeDiscont record);

    int insertSelective(TradeDiscont record);

    TradeDiscont selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("discntId") Integer discntId, @Param("startDate") Date startDate);

    int updateByPrimaryKeySelective(TradeDiscont record);

    int updateByPrimaryKey(TradeDiscont record);
}