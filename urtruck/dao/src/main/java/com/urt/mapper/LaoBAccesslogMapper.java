package com.urt.mapper;

import com.urt.po.LaoBAccesslog;

public interface LaoBAccesslogMapper {
    int deleteByPrimaryKey(Long accessId);

    int insert(LaoBAccesslog record);

    int insertSelective(LaoBAccesslog record);

    LaoBAccesslog selectByPrimaryKey(Long accessId);

    int updateByPrimaryKeySelective(LaoBAccesslog record);

    int updateByPrimaryKey(LaoBAccesslog record);
}