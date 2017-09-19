package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.TradeDiscont;

public interface TradeDiscontExtMapper {

    List<TradeDiscont> selectByTradeId(Long tradeId);

}