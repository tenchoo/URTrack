package com.urt.interfaces.http;

import java.util.Map;

import com.urt.dto.http.QuerySms;
import com.urt.dto.http.SendSmsDto;

public interface SendSmsServer {
	/**
	 * 
	 * @param smsContent
	 * kafka+storm+dobbo来发送
	 */
	public String sendSMS(Map<String,Object> smsContent);
	
	/**
	 * 
	 * @param sendWay 发送方式
	 * @param iccid  iccid
	 * @param phone  电话号码
	 * @return  
	 * 获取发出去的短信是否发送成功
	 */
	public String getReport(String sendWay,String iccid,String phone);
	
	/**
	 * 
	 * @param reqInfo
	 * @return
	 * 单条发送直接调用dubbo来发送
	 */
	public SendSmsDto sendSMSgla(Map<String, String> reqInfo);
	
	/**
	 * 
	 * @param reqInfo
	 * @return
	 * 短信查询
	 */
	public QuerySms querySms(Map<String, String> reqInfo);
}
