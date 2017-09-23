package com.urt.mapper;

import java.math.BigDecimal;

import com.urt.po.FlowOperationLog;

public interface FlowOperationLogMapper {
    int deleteByPrimaryKey(BigDecimal logid);

    int insert(FlowOperationLog record);

    int insertSelective(FlowOperationLog record);

    FlowOperationLog selectByPrimaryKey(BigDecimal logid);

    int updateByPrimaryKeySelective(FlowOperationLog record);

    int updateByPrimaryKey(FlowOperationLog record);
}