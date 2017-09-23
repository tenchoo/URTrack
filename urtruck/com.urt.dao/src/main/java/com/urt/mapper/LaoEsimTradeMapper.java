package com.urt.mapper;

import com.urt.po.LaoEsimTrade;

public interface LaoEsimTradeMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insert(LaoEsimTrade record);

    int insertSelective(LaoEsimTrade record);

    LaoEsimTrade selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(LaoEsimTrade record);

    int updateByPrimaryKey(LaoEsimTrade record);
}