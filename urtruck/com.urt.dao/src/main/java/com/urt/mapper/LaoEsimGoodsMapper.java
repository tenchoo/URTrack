package com.urt.mapper;

import com.urt.po.LaoEsimGoods;

public interface LaoEsimGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimGoods record);

    int insertSelective(LaoEsimGoods record);

    LaoEsimGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimGoods record);

    int updateByPrimaryKey(LaoEsimGoods record);
}