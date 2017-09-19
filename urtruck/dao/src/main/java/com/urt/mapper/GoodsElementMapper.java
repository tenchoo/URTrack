package com.urt.mapper;

import com.urt.po.GoodsElement;

public interface GoodsElementMapper {
    int deleteByPrimaryKey(Integer elementId);

    int insert(GoodsElement record);

    int insertSelective(GoodsElement record);

    GoodsElement selectByPrimaryKey(Integer elementId);

    int updateByPrimaryKeySelective(GoodsElement record);

    int updateByPrimaryKey(GoodsElement record);
}