package com.urt.mapper;

import com.urt.po.EsimLenovoGoodsId;

public interface EsimLenovoGoodsIdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EsimLenovoGoodsId record);

    int insertSelective(EsimLenovoGoodsId record);

    EsimLenovoGoodsId selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EsimLenovoGoodsId record);

    int updateByPrimaryKey(EsimLenovoGoodsId record);
}