package com.urt.mapper;

import com.urt.po.FlowOperationFlowOrder;

public interface FlowOperationFlowOrderMapper {
    int deleteByPrimaryKey(String floworderid);

    int insert(FlowOperationFlowOrder record);

    int insertSelective(FlowOperationFlowOrder record);

    FlowOperationFlowOrder selectByPrimaryKey(String floworderid);

    int updateByPrimaryKeySelective(FlowOperationFlowOrder record);

    int updateByPrimaryKey(FlowOperationFlowOrder record);
}