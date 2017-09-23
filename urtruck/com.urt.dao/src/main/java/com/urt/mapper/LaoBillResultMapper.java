package com.urt.mapper;

import com.urt.po.LaoBillResult;

public interface LaoBillResultMapper {
    int deleteByPrimaryKey(Long balanceId);

    int insert(LaoBillResult record);

    int insertSelective(LaoBillResult record);

    LaoBillResult selectByPrimaryKey(Long balanceId);

    int updateByPrimaryKeySelective(LaoBillResult record);

    int updateByPrimaryKey(LaoBillResult record);
}