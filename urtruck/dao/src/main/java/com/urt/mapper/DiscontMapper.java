package com.urt.mapper;

import com.urt.po.Discont;

public interface DiscontMapper {
    int deleteByPrimaryKey(Integer discontId);

    int insert(Discont record);

    int insertSelective(Discont record);

    Discont selectByPrimaryKey(Integer discontId);

    int updateByPrimaryKeySelective(Discont record);

    int updateByPrimaryKey(Discont record);
}