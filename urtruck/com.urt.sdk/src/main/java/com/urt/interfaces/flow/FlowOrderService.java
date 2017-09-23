package com.urt.interfaces.flow;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;



public interface FlowOrderService {

    /**
     * 校验请求参数是否为空
     * @param requestParams
     * @return
     */
	JSONObject paramCheck(String custId, String appkey,String clientOrderId, String mobile,String packageSize);
	/**
	 * 调用大汉三通流量订购接口,并且把输入和输出参数记录到数据库
	 * @param requestParams
	 * @return
	 */
	JSONObject sentPostToDaHan(JSONObject requestParams,String flowOrderId);
	/**
	 * 第一步初始化流量订单表和记录日志
	 * @param requestParams
	 * @param respObj
	 * @return 
	 */
	String insertFlowOrderAndLog(JSONObject requestParams, JSONObject respObj);
	/**
	 * 大汉三通回调懂的通信，参数校验
	 * @param requestParams
	 * @throws UnsupportedEncodingException 
	 */
	JSONObject callBackCheck(String clientOrderId, String mobile, String reportTime,
			String callBackTime, String status, String errorCode,
			String errorDesc, String intervalTime, String clientSubmitTime,
			String discount,String costMoney, String sign) throws UnsupportedEncodingException;
	/**
	 * 流量订购第三部，数据库记录
	 * @param requestParams
	 * @param resp
	 */
	void recordBackFlowLogThree(JSONObject requestParams,JSONObject resp,String flowOrderId);
	/**
	 * 通过客户端订单号查询流量订单ID
	 * @param clientOrderId
	 * @return
	 */
	String getFlowOrderId(String clientOrderId);
	/**
	 * post 回调第三方系统
	 * @param requestParams
	 * @param flowOrderId
	 * @return
	 */
	JSONObject sentPostToEdge(JSONObject requestParams,String flowOrderId);
}
