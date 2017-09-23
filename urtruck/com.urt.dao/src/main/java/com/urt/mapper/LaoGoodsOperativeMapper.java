package com.urt.mapper;

import java.util.List;

import com.urt.po.LaoGoodsOperative;

public interface LaoGoodsOperativeMapper {
    int deleteByPrimaryKey(Short id);

    int insert(LaoGoodsOperative record);

    int insertSelective(LaoGoodsOperative record);

    LaoGoodsOperative selectByPrimaryKey(Short id);
    List<LaoGoodsOperative> selectAll();

    int updateByPrimaryKeySelective(LaoGoodsOperative record);

    int updateByPrimaryKey(LaoGoodsOperative record);
}