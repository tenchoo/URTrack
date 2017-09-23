package com.urt.mapper.ext;

import com.urt.po.TradeSvcState;

public interface TradeSvcStateExtMapper {

    TradeSvcState selectByTradeId(Long tradeId);

}