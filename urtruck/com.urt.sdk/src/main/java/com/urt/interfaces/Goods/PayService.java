package com.urt.interfaces.Goods;

import java.util.Map;

import com.urt.dto.unicom.PaymentDto;


/**
 * 支付方法
 * 
 * @author zhaoyf
 */
public interface PayService {

	//收银台支付完回调
	public Map<String,Object> aliPayCallBacktMap(PaymentDto payment)throws Exception;
	
	//查询得到用户账号信息和流量包信息
//	public Map<String,Object> getPaymentInfo(String userId);
	
	//生成支付信息  userId, userName, flowSize, payAmount, iccid, lpsust
	public Map<String,String> toAliPay(String userId,String userName,Double fee, String iccid,String lpsust,String tradeId);
}
