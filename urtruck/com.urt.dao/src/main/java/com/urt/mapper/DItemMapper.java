package com.urt.mapper;

import com.urt.po.DItem;

public interface DItemMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(DItem record);

    int insertSelective(DItem record);

    DItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(DItem record);

    int updateByPrimaryKey(DItem record);
}