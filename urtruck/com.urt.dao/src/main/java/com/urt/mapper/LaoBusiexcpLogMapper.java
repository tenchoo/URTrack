package com.urt.mapper;

import com.urt.po.LaoBusiexcpLog;

public interface LaoBusiexcpLogMapper {
    int deleteByPrimaryKey(Long excpId);

    int insert(LaoBusiexcpLog record);

    int insertSelective(LaoBusiexcpLog record);

    LaoBusiexcpLog selectByPrimaryKey(Long excpId);

    int updateByPrimaryKeySelective(LaoBusiexcpLog record);

    int updateByPrimaryKey(LaoBusiexcpLog record);
}