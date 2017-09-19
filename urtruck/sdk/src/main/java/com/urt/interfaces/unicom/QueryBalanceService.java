package com.urt.interfaces.unicom;

import java.util.Map;

import com.urt.dto.unicom.DeviceDto;


/**
 * 查询余额方法
 * 
 * @author sunhao 2016年8月24日13:59:39
 */
public interface QueryBalanceService {

	// 查询流量余额
	public Map<String, Object> queryFlow(DeviceDto device);
	
	//查询消费记录
	public Map<String,Object> queryPurchaseHistory(DeviceDto device, String accountId, String pageNumber);
	
	//查询充值记录
	public Map<String,Object> findChargeOrderPage(String startTime,String endTime, String userId,int curPage, int pageSize );
}
