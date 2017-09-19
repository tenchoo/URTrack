package com.urt.mapper;

import com.urt.po.LaoBPaylog;

public interface LaoBPaylogMapper {
    int deleteByPrimaryKey(Long chargeId);

    int insert(LaoBPaylog record);

    int insertSelective(LaoBPaylog record);

    LaoBPaylog selectByPrimaryKey(Long chargeId);

    int updateByPrimaryKeySelective(LaoBPaylog record);

    int updateByPrimaryKey(LaoBPaylog record);
}