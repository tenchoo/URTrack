package com.urt.mapper;

import com.urt.po.GoodsRelease;

public interface GoodsReleaseMapper {
    int deleteByPrimaryKey(Integer goodsReleaseId);

    int insert(GoodsRelease record);

    int insertSelective(GoodsRelease record);

    GoodsRelease selectByPrimaryKey(Integer goodsReleaseId);

    int updateByPrimaryKeySelective(GoodsRelease record);

    int updateByPrimaryKey(GoodsRelease record);
}