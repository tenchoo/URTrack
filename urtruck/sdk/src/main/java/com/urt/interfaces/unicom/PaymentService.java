package com.urt.interfaces.unicom;

import java.util.Map;

import com.urt.dto.unicom.PaymentDto;


/**
 * 支付方法
 * 
 * @author sunhao 2016年8月24日13:59:39
 */
public interface PaymentService {

	//收银台支付完回调
	public Map<String,Object> aliPayCallBack(PaymentDto payment);
	
	//查询得到用户账号信息和流量包信息
	public Map<String,Object> getPaymentInfo(String userId);
	
	//查询得到用户账号信息和流量包信息
    public Map<String,Object> getPaymentMsg(String userId);
    
	//创建订单
	public Map<String,String> toAliPay(String userId, String userName, String flowSize, Double payAmount, String iccid, String lpsust);
	//h5 微信支付功能
	public Map<String,String> weixinH5Pay(String ip, String openId, String userId, String userName, String flowSize, Double payAmount, String iccid, String lpsust);
	//判断iccid是否是h5
	public boolean ifIccidOfH5(String iccid);
	
}
