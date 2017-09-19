package com.urt.interfaces.http.maichi;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
public interface FlowPayService {
    /**
     * 第三方请求参数不为空时,第一次更新数据到lao_flowoperation_floworder
     * @param jsonObj
     * @return
     */
	String insertFlowOrderOne(JSONObject jsonObj );
	/**
     * 第三方请求参数不为空时,更新数据到lao_flowoperation_log表
     * @param jsonObj
     * @return
     */
	String insertFlowOrderFlowAndLog(JSONObject insertFlowAndLogParam,JSONObject insertFlowParam,JSONObject repResult);
	/**
	 * 请求麦驰接口插入数据到lao_flowoperation_log表
	 * @param requestParamMC
	 * @param resultHttpGetJSON
	 * @return
	 */
	String  insertFlowAndLogTwo(JSONObject requestParamMC,JSONObject resultHttpGetJSON,String flowOrderId);
	
    /**
     * 校验第三方调用接口请求过来的参数
     * @param username
     * @param mobile
     * @param transId
     * @param productId
     * @param appId
     * @return JSONObject
     */
	JSONObject paramCheck(String mobile, String transId, String productId, String appId,String appkey,String custId);
	
	/**
	 * 麦驰回调参数检查
	 * @param dbKeyId
	 * @param userAppId
	 * @param mobile
	 * @param productId
	 * @param productName
	 * @param productPrice
	 * @param productPrice2
	 * @param productProvince
	 * @param isp
	 * @param createDate
	 * @param processDate
	 * @param reportDate
	 * @param billId
	 * @param status
	 * @return JSONObject
	 */
	JSONObject callBackCheck(String dbKeyId, String userAppId, String mobile, String productId, String productName,
			String productPrice, String productProvince, String isp, String createDate,
			String processDate, String reportDate, String billId, String status);
     
	/**
	 * 通过BillId 获取FlowOrderId
	 * @param transId
	 * @return
	 */
	public String getFlowOrderIdByBillId(String BillId);
	/**
	 * 第三步 更新数据库
	 * @param flowOrderId
	 * @param requestParam
	 * @param respParam
	 */
	void  recordFlowOrderAndLogThree(String flowOrderId,JSONObject requestParam,JSONObject respParam);
	/**
	 * 第四步 更新数据库
	 * @param flowOrderId
	 * @param requestJson
	 */
	JSONObject recordFlowOrderAndLogFour(String flowOrderId, JSONObject requestJson);
	/**
	 * 推送状态报告给第三方系统
	 * @param resultParam
	 * @return
	 */
	public  JSONObject sendPayResult(JSONObject resultParam);
	/**
	 * 更新流量订单日志表中的第四步  响应参数
	 * @param flowOrderIdList
	 */
	void upDataFlowLogRespParam(List<String> flowOrderIdList,JSONObject respParam);
	/**
	 * 根据客户Id查询订单
	 * @param custId
	 * @return
	 */
	String selectTokeyByCustId(String custId);
	
}
