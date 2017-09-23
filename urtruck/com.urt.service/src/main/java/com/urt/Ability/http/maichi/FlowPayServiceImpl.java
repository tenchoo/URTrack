package com.urt.Ability.http.maichi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.interfaces.http.maichi.FlowPayService;
import com.urt.mapper.FlowOperationFlowOrderMapper;
import com.urt.mapper.FlowOperationLogMapper;
import com.urt.mapper.ext.FlowOperationFlowOrderExtMapper;
import com.urt.mapper.ext.FlowOperationLogExtMapper;
import com.urt.mapper.ext.LaoKeyManagementExtMapper;
import com.urt.po.FlowOperationFlowOrder;
import com.urt.po.FlowOperationLog;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("flowPayService")
@Transactional(propagation = Propagation.REQUIRED)
public class FlowPayServiceImpl implements FlowPayService {
	private final static String BUSINESSURL = "http://sms.weiyingjia.cn:8080/ytx/data/callback/newlenovoReportCall.jsp"; // 商户地址
	private final static String FLOWPAYURL = "http://sms.weiyingjia.cn:8080/ytx/data/setProductByMobile2.jsp"; // 麦驰支付接口Url
	private final static String appSecret = "d46058fc4778c575ff8f6deb426b4c39";
	private final static String CALLSERVER = "flowOrder";
	private final static String USERNAME = "lxdd";
	private final static String DDNAME = "懂的通信";
	private final static String MCNAME = "麦驰";
	private final static String DSFNAME = "第三方系统";
	// from lao_flowoperation_floworder表接口
	@Resource
	private FlowOperationFlowOrderMapper flowOperationFlowOrderMapper;
	// lao_flowoperation_log表接口
	@Resource
	private FlowOperationLogMapper flowOperationLogMapper;

	@Resource
	private FlowOperationLogExtMapper flowOperationLogExtMapper;

	@Resource
	private FlowOperationFlowOrderExtMapper flowOperationFlowOrderExtMapper;

	@Autowired
	LaoKeyManagementExtMapper managermentExtDao;
	Logger log = Logger.getLogger(getClass());

	// 校验第三方调用接口请求过来的参数
	@Override
	public JSONObject paramCheck(String mobile, String transId, String productId, String appId, String appKey,
			String custId) {
		HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

		// 返回给第三方请求的结果
		JSONObject repResult = new JSONObject();

		// 请求麦驰接口
		JSONObject resquestParam = new JSONObject();

		// 请求懂得通信接口
		JSONObject requestDDParam = new JSONObject();

		String flowOrderId = null;

		if ("".equals(mobile.trim()) || "".equals(transId.trim()) || "".equals(productId.trim())
				|| "".equals(appId.trim()) || "".equals(custId.trim())) {
			repResult.put("RESP_CODE", "01");
			repResult.put("RESP_DESC", "缺少必要的参数");
			return repResult;
		} else if (transId.length() < 16) {
			repResult.put("RESP_CODE", "002");
			repResult.put("RESP_DESC", "流水号不合法");
			return repResult;
		} else {
			// 根据custId去数据库查出appkey
			String appkeyDB = managermentExtDao.selectAppKeyByCustId(custId);
			if (appkeyDB == null || "".equals(appkeyDB)) {
				repResult.put("RESP_CODE", "003");
				repResult.put("RESP_DESC", "未开通流量业务");
				return repResult;

			} else if (!appKey.trim().equals(appkeyDB.trim())) {
				repResult.put("RESP_CODE", "004");
				repResult.put("RESP_DESC", "appKey不正确");
				return repResult;
			}
		}
		requestDDParam.put("mobile", mobile);
		requestDDParam.put("transId", transId);
		requestDDParam.put("productId", productId);
		requestDDParam.put("appId", appId);
		requestDDParam.put("requestTime", fromatDate());
		requestDDParam.put("appKey",appKey);
		requestDDParam.put("custId",custId);

		JSONObject insertFlowAndLogParam = new JSONObject();
		JSONObject insertFlowParam = new JSONObject();
		insertFlowParam.put("transId", transId);
		insertFlowParam.put("mobile", mobile);
		insertFlowParam.put("custId",custId);
		insertFlowParam.put("productId", productId);
		// 第一次请求参数全部不为空,插入参数到from lao_flowoperation_floworder表中
		flowOrderId = insertFlowOrderOne(insertFlowParam);
		if ("".equals(flowOrderId.trim())) {
			repResult.put("RESP_CODE", "29");
			repResult.put("RESP_DESC", "订单提交失败");
			repResult.put("clientOrderId", transId);

		} else {
			repResult.put("RESP_CODE", "00");
			repResult.put("RESP_DESC", "订单提交成功");
			repResult.put("clientOrderId", transId);
		}
		insertFlowAndLogParam.put("requestName", DSFNAME);
		insertFlowAndLogParam.put("responseName", DDNAME);
		insertFlowAndLogParam.put("flowOrderId", flowOrderId);
		// 第一次记录第三方请求懂得通信记录到lao_flowoperation_log表中
		insertFlowOrderFlowAndLog(insertFlowAndLogParam, requestDDParam, repResult);

		String sign = DigestUtils.md5Hex(USERNAME + transId + productId + mobile + appId + appSecret);
		StringBuffer sb = new StringBuffer();
		StringBuffer url = sb.append(FLOWPAYURL + "?" + "username" + "=" + USERNAME + "&" + "transId" + "=" + transId
				+ "&" + "productId" + "=" + productId + "&" + "mobile" + "=" + mobile + "&" + "appId" + "=" + appId
				+ "&" + "sign" + "=" + sign);
		String resultHttpGet = httpClientUtil.sendHttpGet(url.toString());
		resquestParam.put("REQUESTNAME", DDNAME);
		resquestParam.put("RESPONESENAME", MCNAME);

		JSONObject resultHttpGetJSON = new JSONObject();
		resultHttpGetJSON = resultHttpGetJSON.parseObject(resultHttpGet);
		String billId = resultHttpGetJSON.getString("billId");
		// 发送完请求更新插入数据到lao_flowoperation_log
		JSONObject requestParamMC = new JSONObject();
		requestParamMC.put("username", USERNAME);
		requestParamMC.put("transId", transId);
		requestParamMC.put("productId", productId);
		requestParamMC.put("mobile", mobile);
		requestParamMC.put("appId", appId);
		requestParamMC.put("sign", sign);
		if (billId != null) {
			// 根据flowOrderId更新到billId lao_flowoperation_floworder
			FlowOperationFlowOrder flowOperationFlowOrder = new FlowOperationFlowOrder();
			flowOperationFlowOrder.setFloworderid(flowOrderId);
			flowOperationFlowOrder.setParaname1(billId);
			flowOperationFlowOrder.setStepstate2("2");
			flowOperationFlowOrder.setStepstatetime2(new Date());
			int result = flowOperationFlowOrderMapper.updateByPrimaryKeySelective(flowOperationFlowOrder);
			if (result > 0) {
				repResult.put("RESP_CODE", "00");
				repResult.put("RESP_DESC", "订单提交成功");
			}

		} else {
			FlowOperationFlowOrder flowOperationFlowOrder = new FlowOperationFlowOrder();
			flowOperationFlowOrder.setFloworderid(flowOrderId);
			flowOperationFlowOrder.setStepstate2("2");
			flowOperationFlowOrder.setStepstatetime2(new Date());
			flowOperationFlowOrder.setIssuccess("no");
			int result = flowOperationFlowOrderMapper.updateByPrimaryKeySelective(flowOperationFlowOrder);
			if (result > 0) {
				repResult.put("RESP_CODE", "29");
				repResult.put("RESP_DESC", "订单提交失败");
			}
		}
		if ("success".equals(resultHttpGetJSON.getString("message"))) {
			resultHttpGetJSON.put("CALLISSUCCESS", "yes");
		} else {
			resultHttpGetJSON.put("CALLISSUCCESS", "no");

		}
		insertFlowAndLogTwo(requestParamMC, resultHttpGetJSON, flowOrderId);
		log.debug("exit the method paramCheck");
		return repResult;
	}

	/**
	 * 插入数据到lao_flowoperation_floworder表中
	 */
	@Override
	public String insertFlowOrderOne(JSONObject insertFlowParam) {
		// 插入数据到lao_flowoperation_floworder表中
		FlowOperationFlowOrder flowOperationFlowOrder = new FlowOperationFlowOrder();
		// 生成主键
		String flowOrderId = ZkGenerateSeq.getIdSeq(SeqID.FLOWORDER_ID);
		flowOperationFlowOrder.setFloworderid(flowOrderId); // 主键Id
		flowOperationFlowOrder.setClientorderid(insertFlowParam.getString("transId"));
		flowOperationFlowOrder.setMobile(insertFlowParam.getString("mobile"));
		flowOperationFlowOrder.setCustchannelid(insertFlowParam.getString("custId"));
		flowOperationFlowOrder.setPackagesize(insertFlowParam.getString("productId"));
		flowOperationFlowOrder.setStepstate1("1"); // 第一次请求对库操作
		flowOperationFlowOrder.setStepstatetime1(new Date());
		flowOperationFlowOrderMapper.insert(flowOperationFlowOrder);
		log.debug("exit the method insertFlowOrderOne");
		return flowOrderId;
	}

	// 通过transId 获取FlowOrderId
	public String getFlowOrderIdByBillId(String billId) {
		String flowOrderId = flowOperationFlowOrderExtMapper.getFlowOrderIdByBillId(billId);
		if (flowOrderId==null) {
			return null;
		}
		log.debug("enter the method getFlowOrderIdByBillId");
		return flowOrderId;
	}

	// 第三方请求参数不为空时,第一次更新数据到lao_flowoperation_log表
	public String insertFlowOrderFlowAndLog(JSONObject insertFlowAndLogParam, JSONObject resquestParam,
			JSONObject repResult) {
		FlowOperationLog flowOperationLog = new FlowOperationLog();
		flowOperationLog.setFloworderid(insertFlowAndLogParam.getString("flowOrderId"));
		flowOperationLog.setStepstate("1");
		flowOperationLog.setRequestsystem(insertFlowAndLogParam.getString("requestName"));
		flowOperationLog.setResponsesystem(insertFlowAndLogParam.getString("responseName"));
		flowOperationLog.setRequestparm(resquestParam.toJSONString());
		flowOperationLog.setResponseparm(repResult.toJSONString());
		flowOperationLog.setCallissuccess("yes");
		flowOperationLog.setCallserver(CALLSERVER);
		flowOperationLog.setCalltime(new Date());
		flowOperationLog.setRespcode(repResult.getString("RESP_CODE"));
		flowOperationLog.setRespdesc(repResult.getString("RESP_DESC"));
		flowOperationLogMapper.insertSelective(flowOperationLog);
		log.debug("exit the method insertFlowOrderFlowAndLog");
		return null;
	}

	// 懂得通信请求麦驰,写入flowLog表中
	public String insertFlowAndLogTwo(JSONObject requestParamMC, JSONObject resultHttpGetJSON, String flowOrderId) {
		FlowOperationLog flowOperationLog = new FlowOperationLog();
		flowOperationLog.setRequestparm(requestParamMC.toJSONString());
		flowOperationLog.setResponseparm(resultHttpGetJSON.toJSONString());
		flowOperationLog.setRequestsystem(DDNAME);
		flowOperationLog.setResponsesystem(MCNAME);
		flowOperationLog.setCallserver(CALLSERVER);
		flowOperationLog.setFloworderid(flowOrderId);
		flowOperationLog.setCalltime(new Date());
		flowOperationLog.setParaname1(resultHttpGetJSON.getString("billId"));
		flowOperationLog.setRespcode(resultHttpGetJSON.getString("code"));
		flowOperationLog.setRespdesc(resultHttpGetJSON.getString("message"));
		flowOperationLog.setStepstate("2");
		flowOperationLog.setCallissuccess(resultHttpGetJSON.getString("CALLISSUCCESS"));
		flowOperationLogMapper.insertSelective(flowOperationLog);
		log.debug("exit the method insertFlowAndLogTwo");
		return null;
	}

	// 麦驰回调参数检查
	@Override
	public JSONObject callBackCheck(String dbKeyId, String userAppId, String mobile, String productId,
			String productName, String productPrice, String productProvince, String isp, String createDate,
			String processDate, String reportDate, String billId, String status) {
		log.debug("enter the method callBackCheck");
		JSONObject respJSON = new JSONObject();
		String resultCode = "1111"; // 状态码
		String resultMsg = "参数校验失败"; // 状态提示信息

		JSONObject resquestObj = new JSONObject();

		if (dbKeyId != null && !"".equals(dbKeyId.trim())) {
			resquestObj.put("dbKeyId", dbKeyId);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}
		if (userAppId != null && !"".equals(userAppId.trim())) {

			resquestObj.put("userAppId", userAppId);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}
		if (mobile != null && !"".equals(mobile.trim())) {
			resquestObj.put("mobile", mobile);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}
		if (productId != null && !"".equals(productId.trim())) {
			resquestObj.put("productId", productId);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (productName != null && !"".equals(productName.trim())) {
			resquestObj.put("productName", productName);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (productPrice != null && !"".equals(productPrice.trim())) {
			resquestObj.put("productPrice", productPrice);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (productProvince != null && !"".equals(productProvince.trim())) {
			resquestObj.put("productProvince", productProvince);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (isp != null && !"".equals(isp.trim())) {
			resquestObj.put("isp", isp);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (createDate != null && !"".equals(createDate.trim())) {
			resquestObj.put("createDate", createDate);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (processDate != null && !"".equals(processDate.trim())) {
			resquestObj.put("processDate", processDate);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (reportDate != null && !"".equals(reportDate.trim())) {
			resquestObj.put("reportDate", reportDate);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (billId != null && !"".equals(billId.trim())) {
			resquestObj.put("billId", billId);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}

		if (status != null && !"".equals(status.trim())) {
			resquestObj.put("status", status);
		} else {
			respJSON.put("RESP_CODE", resultCode);
			respJSON.put("RESP_DESC", resultMsg);
			return respJSON;
		}
		resultCode = "0000";
		resultMsg = "参数校验成功";
		respJSON.put("RESP_CODE", resultCode);
		respJSON.put("RESP_DESC", resultMsg);
		log.debug("exit the method callBackCheck");
		return respJSON;
	}

	/**
	 * 格式化时间
	 */
	public static String fromatDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(date);
	}

	// 第三步更新数据 ,麦驰回调懂得通信接口
	@Override
	public void recordFlowOrderAndLogThree(String flowOrderId, JSONObject requestParam, JSONObject respParam) {
		// 更新FlowOrder表
		FlowOperationFlowOrder flowOperationFlowOrder = new FlowOperationFlowOrder();
		flowOperationFlowOrder.setFloworderid(flowOrderId);
		String string = requestParam.getString("productPrice");
		flowOperationFlowOrder.setCallbackdostmoney(string);
		flowOperationFlowOrder.setStepstate3("3");
		flowOperationFlowOrder.setStepstatetime3(new Date());
		flowOperationFlowOrderMapper.updateByPrimaryKeySelective(flowOperationFlowOrder);
		// 更新流量订单日志表
		FlowOperationLog flowOperationLog = new FlowOperationLog();
		flowOperationLog.setRequestsystem(MCNAME);
		flowOperationLog.setResponsesystem(DDNAME);
		flowOperationLog.setFloworderid(flowOrderId);
		flowOperationLog.setCallserver(CALLSERVER);
		flowOperationLog.setCalltime(new Date());
		flowOperationLog.setRequestparm(requestParam.toJSONString());
		flowOperationLog.setResponseparm(respParam.toJSONString());
		flowOperationLog.setStepstate("3");
		flowOperationLog.setParaname1(requestParam.getString("billId"));
		String status = requestParam.getString("status");
		if ("充值成功".equals(status)) {
			flowOperationLog.setCallissuccess("yes");
			flowOperationLog.setRespcode("888"); // 充值成功标记码
			flowOperationLog.setRespdesc("充值成功"); // 充值成功
		} else {
			flowOperationLog.setCallissuccess("no");
			flowOperationLog.setRespcode("444"); // 充值失败标记码
			flowOperationLog.setRespdesc("充值失败"); // 失败
		}
		flowOperationLogMapper.insertSelective(flowOperationLog);
		log.debug("exit the method recordFlowOrderAndLogThree");
	}

	// 第四步 更新数据库
	@Override
	public JSONObject recordFlowOrderAndLogFour(String flowOrderId, JSONObject requestJson) {
		// 更新流量订单表
		FlowOperationFlowOrder flowOperationFlowOrder = new FlowOperationFlowOrder();
		flowOperationFlowOrder.setFloworderid(flowOrderId);
		flowOperationFlowOrder.setStepstate4("4");
		flowOperationFlowOrder.setStepstatetime4(new Date());
		if ("充值成功".equals(requestJson.getString("status"))) {
			flowOperationFlowOrder.setIssuccess("yes");
		} else {
			flowOperationFlowOrder.setIssuccess("no");
		}
		flowOperationFlowOrderMapper.updateByPrimaryKeySelective(flowOperationFlowOrder);
		// 根据flowOrderId查询transId
		FlowOperationFlowOrder flowOrder = flowOperationFlowOrderMapper.selectByPrimaryKey(flowOrderId);
		// 更新流量订单日志表
		FlowOperationLog flowOperationLog = new FlowOperationLog();
		flowOperationLog.setFloworderid(flowOrderId);
		flowOperationLog.setCallserver(CALLSERVER);
		flowOperationLog.setRequestsystem(DDNAME);
		flowOperationLog.setResponsesystem(DSFNAME);
		flowOperationLog.setCalltime(new Date());
		flowOperationLog.setParaname1(requestJson.getString("billId"));
		if ("充值成功".equals(requestJson.getString("status"))) {
			flowOperationLog.setRespcode("888");
			flowOperationLog.setRespdesc("充值成功");
			flowOperationLog.setCallissuccess("yes");
		} else {
			flowOperationLog.setRespcode("444");
			flowOperationLog.setRespdesc("充值失败");
			flowOperationLog.setCallissuccess("no");
		}
		flowOperationLog.setRequestparm(requestJson.toJSONString());
		flowOperationLog.setStepstate("4");
		flowOperationLogMapper.insertSelective(flowOperationLog);

		// 推送状态报告给第三方系统
		JSONObject requestObj = new JSONObject();
		requestObj.put("clientOrderId", flowOrder.getClientorderid());
		requestObj.put("flowOrderId", flowOrderId);
		requestObj.put("status", requestJson.getString("status"));
		requestObj.put("productProvince", requestJson.getString("productProvince"));
		requestObj.put("mobile", requestJson.getString("mobile"));
		requestObj.put("appId", "2");
		requestObj.put("productId", requestJson.getString("productId"));
		requestObj.put("productName", requestJson.getString("productName"));
		requestObj.put("productPrice", requestJson.getString("productPrice"));
		requestObj.put("createDate", requestJson.getString("createDate"));
		requestObj.put("processDate", requestJson.getString("processDate"));
		requestObj.put("reportTime", new Date());
		requestObj.put("isp", requestJson.getString("isp"));
		log.debug("exit the method recordFlowOrderAndLogFour");
		return requestObj;
	}

	// 推送状态报告给第三方系统
	public JSONObject sendPayResult(JSONObject resultParam) {

		JSONObject resultObj = new JSONObject();
		HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
		log.info("推送给聚通达的=================数据"+resultParam.toJSONString());
		String respResult = httpClientUtil.sendHttpPost(BUSINESSURL, resultParam.toJSONString());
		log.info("推送给聚通达----返回值----"+respResult);
	    if (null==respResult) {
	    	resultObj.put("RESP_CODE", "1111");
			resultObj.put("RESP_DESC", "处理失败");
		}else{
			JSONObject jsonObject = new JSONObject();
			jsonObject = jsonObject.parseObject(respResult);
			if ("0000".equals(jsonObject.getString("RESP_CODE"))) {
				resultObj.put("RESP_CODE", "0000");
				resultObj.put("RESP_DESC", "处理成功");
				
			}else{
				resultObj.put("RESP_CODE", "1111");
				resultObj.put("RESP_DESC", "处理失败");
			}
		}
		return resultObj;
	}

/*	public static void main(String[] args) {
		
		//{"appId":"2","appKey":"BU1100PZ4ECeQO7H3FheuKhBU0507h","clientOrderId":"jvtdandlenovo1484904354750","custId":"3071355310000954","mobiles":"15076988366","packageSize":"1"}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mobiles", "15076988366");
		jsonObject.put("packageSize", "1");
		jsonObject.put("appId", "2");
		jsonObject.put("appKey", "BU1100PZ4ECeQO7H3FheuKhBU0507h");
		jsonObject.put("custId", "3071355310000954");
		jsonObject.put("clientOrderId", "jvtdandlenovo1484904354750");
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String resp = instance.sendHttpPostOfJson("http://127.0.0.1/flowPay/flowOrder", jsonObject.toString());
		System.out.println(resp);
	}
*/
	// 回调测试
	
	public static void main(String[] args) {
		String respResult="";
		JSONObject jsonObject = new JSONObject();
		jsonObject = jsonObject.parseObject(respResult);
		System.out.println(jsonObject);
		
		/*HttpClientUtil instance = HttpClientUtil.getInstance();
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("dbKeyId", "2932634");
		jsonObj2.put("userAppId", "310");
		jsonObj2.put("mobile", "18286870529");
		jsonObj2.put("productId", "5");
		jsonObj2.put("productName", "500MB流量包");
		jsonObj2.put("productPrice", "30000");
		jsonObj2.put("productProvince", "全国");
		jsonObj2.put("isp", "移动");
		jsonObj2.put("createDate", "2017-03-06 23:56:04.0");
		jsonObj2.put("processDate", "2017-03-06 23:56:06.0");
		jsonObj2.put("reportDate", "2017-03-06 23:59:52.0");
		jsonObj2.put("billId", "e09be894-31c6-413e-b3ad-fd5188eccfff");
		jsonObj2.put("status", "充值成功");
		HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
		System.out.println("================="+jsonObj2.toJSONString());
		String respResult = httpClientUtil.sendHttpPost(BUSINESSURL, jsonObj2.toJSONString());
		*/
		System.out.println(jsonObject.toJSONString());
	/*	
		JSONObject jsonObj1 = new JSONObject();
		jsonObj1.put("dbKeyId", "872711");
		jsonObj1.put("userAppId", "310");
		jsonObj1.put("mobile", "13331086792");
		jsonObj1.put("productId", "18");
		jsonObj1.put("productName", "5MB流量包");
		jsonObj1.put("productPrice", "1000");
		jsonObj1.put("productProvince", "全国");
		jsonObj1.put("isp", "电信");
		jsonObj1.put("createDate", "2017-01-23 13:20:12.0");
		jsonObj1.put("processDate", "2017-01-23 13:20:35.0");
		jsonObj1.put("reportDate", "2017-01-23 13:20:36.0");
		jsonObj1.put("billId", "e09be894-31c6-413e-b3ad-fd5188eccfff");
		jsonObj1.put("status", "充值成功");
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObj2);
//		jsonArray.add(jsonObj1);
		System.out.println(jsonArray.toString());
		String jsonString = jsonArray.toString();
		long stats = System.currentTimeMillis();
		String resp = instance.sendHttpPostOfJson("http://gla.lenovo.com/flowPay/OrderCallBack", jsonString);
		long end = System.currentTimeMillis();
		System.err.println(end-stats);
		System.out.println(resp);*/
	}
	// 更新流量订单日志表中的第四步 响应参数
	@Override
	public void upDataFlowLogRespParam(List<String> flowOrderIdList, JSONObject respParam) {
		for (String string : flowOrderIdList) {
			flowOperationLogExtMapper.updateResponseparm(respParam.toString(), string);
		}
	}
    //根据客户Id查询
	@Override
	public String selectTokeyByCustId(String custId) {
		String appkeyDB = managermentExtDao.selectAppKeyByCustId(custId);
		if (appkeyDB==null && "".equals(appkeyDB)) {
			return null;
		}
        return appkeyDB;
	}

}
