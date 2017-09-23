package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;

public interface FlowOperationFlowOrderExtMapper {

	String getFlowOrderId(String clientOrderId);
	
	int getFlowOrderCount(String flowOrderId);

	String getHttpUrl(@Param("flowOrderId")String flowOrderId);
	/**
	 * 通过Billid获取订单编号
	 * @param billId
	 * @return
	 */
    String getFlowOrderIdByBillId(String billId);
}