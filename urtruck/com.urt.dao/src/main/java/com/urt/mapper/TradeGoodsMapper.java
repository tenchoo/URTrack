package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeGoods;

public interface TradeGoodsMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("goodsId") Integer goodsId, @Param("startDate") Date startDate);

    int insert(TradeGoods record);

    int insertSelective(TradeGoods record);

    TradeGoods selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("goodsId") Integer goodsId, @Param("startDate") Date startDate);

    int updateByPrimaryKeySelective(TradeGoods record);

    int updateByPrimaryKey(TradeGoods record);
}