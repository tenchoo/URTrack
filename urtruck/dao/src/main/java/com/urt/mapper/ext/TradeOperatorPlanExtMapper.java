package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.TradeOperatorPlan;

public interface TradeOperatorPlanExtMapper {

    List<TradeOperatorPlan> selectByTradeId(Long tradeId);

}