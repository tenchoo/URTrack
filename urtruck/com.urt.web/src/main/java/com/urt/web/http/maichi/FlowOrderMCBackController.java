package com.urt.web.http.maichi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.serialize.support.json.JsonSerialization;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.urt.common.util.JsonUtil;
import com.urt.interfaces.http.maichi.FlowPayService;

/**
 * 第三方订流量时麦驰回调的服务
 * 
 * @author
 */
@Controller
@RequestMapping("/flowPay")
public class FlowOrderMCBackController {

	private static Logger log = Logger.getLogger(FlowOrderMCBackController.class);

	@Resource
	private FlowPayService flowPayService;

	@RequestMapping(value = "OrderCallBack", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject callBackOrder(HttpServletRequest request, HttpServletResponse response) {
		log.info("enter the method callBackOrder");
		System.out.println("》》》》》麦驰开始回调懂得通信的下单回调接口");
		JsonArray requestJsonArray = null;
		JSONObject respObj = new JSONObject();
		// 响应给麦驰的json数据
		try {
			ServletInputStream inputStream = request.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String b = null;
			while ((b = br.readLine()) != null) {
				sb.append(b + System.getProperty("line.separator"));
			}
			System.out.println("麦驰调用回调接收到的值:" + sb.toString());
			log.info("exit the method callBackOrder" + sb.toString());
			requestJsonArray = JsonUtil.fromJson(sb.toString(), JsonArray.class);
		} catch (Exception e) {
			e.printStackTrace();
			respObj.put("RESP_CODE", "1111");
			respObj.put("RESP_DESC", "请求处理失败");
			log.info(">>>>>>>>>>>>>>>>>>>接收参数异常+++++++++++++++++++++++++++");
			return respObj;
		}
		if (requestJsonArray == null) {
			respObj.put("RESP_CODE", "1111");
			respObj.put("RESP_DESC", "请求处理失败");
			log.info(">>>>>>>>>>>>>麦驰请求为空+++++++++++++++++++++=");
			return respObj;
		}
		// 推送报告给第三方系统
		JSONArray sendJson = new JSONArray();
		List<String> list = new ArrayList<>();
		List<String> flowOrderIdList = new ArrayList<>();

		try {
			for (int i = 0; i < requestJsonArray.size(); i++) {
				JsonElement jsonElement = requestJsonArray.get(i);
				JsonObject requestJson = jsonElement.getAsJsonObject();
				String dbKeyId = requestJson.get("dbKeyId").getAsString();
				String userAppId = requestJson.get("userAppId").getAsString();
				String mobile = requestJson.get("mobile").getAsString();
				String productId = requestJson.get("productId").getAsString();
				String productName = requestJson.get("productName").getAsString();
				String productPrice = requestJson.get("productPrice").getAsString();
				String productProvince = requestJson.get("productProvince").getAsString();
				String isp = requestJson.get("isp").getAsString();
				String createDate = requestJson.get("createDate").getAsString();
				String processDate = requestJson.get("processDate").getAsString();
				String reportDate = requestJson.get("reportDate").getAsString();
				String billId = requestJson.get("billId").getAsString();
				String status = requestJson.get("status").getAsString();
				// 麦驰回调更新 状态报告,响应结果给麦驰
				
				JSONObject  jsonObject = flowPayService.callBackCheck(dbKeyId, userAppId, mobile, productId, productName, productPrice,
						productProvince, isp, createDate, processDate, reportDate, billId, status);
				if ("1111".equals(respObj.getString("RESP_CODE"))) {
					return jsonObject;
				}
				JSONObject requestObj = new JSONObject();
				requestObj.put("dbKeyId", dbKeyId);
				requestObj.put("userAppId", userAppId);
				requestObj.put("mobile", mobile);
				requestObj.put("productId", productId);
				requestObj.put("productName", productName);
				requestObj.put("productPrice", productPrice);
				requestObj.put("productProvince", productProvince);
				requestObj.put("isp", isp);
				requestObj.put("createDate", createDate);
				requestObj.put("processDate", processDate);
				requestObj.put("reportDate", reportDate);
				requestObj.put("billId", billId);
				requestObj.put("status", status);

				// 获取订单编号更新数据库
				String flowOrderId = flowPayService.getFlowOrderIdByBillId(billId);
				if (flowOrderId==null) {
					respObj.put("RESP_CODE", "1111");
					respObj.put("RESP_DESC", "请求处理失败");
					log.info("exit the method callBackOrder");
					return respObj;
				}
				flowOrderIdList.add(flowOrderId);
				// 三 更新数据库
				flowPayService.recordFlowOrderAndLogThree(flowOrderId, requestObj, jsonObject);
				// 四 更新数据库
				JSONObject resultObj = flowPayService.recordFlowOrderAndLogFour(flowOrderId, requestObj);
				list.add(resultObj.getString("clientOrderId"));
				sendJson.add(resultObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			respObj.put("RESP_CODE", "1111");
			respObj.put("RESP_DESC", "请求处理失败");
			log.info(">>>>>>>>>>>>>推送异常+++++++++++++++");
			return respObj;
		}
		// 排序加密推送
		Collections.sort(list);
		String str = null;
		for (int j = 0; j < list.size(); j++) {
			str += list.get(j);
		}
		String sign = degistByMD5(str);

		JSONObject sendJsonObj = new JSONObject();
		sendJsonObj.put("FlowInfo", sendJson.toString());
		sendJsonObj.put("sign", sign);

		// 发送状态报告给第三方系统
		respObj = flowPayService.sendPayResult(sendJsonObj);
		// 更新数据库中第四步的响应字段
		flowPayService.upDataFlowLogRespParam(flowOrderIdList, respObj);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		return respObj;
	}

	public static String degistByMD5(String str) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
}
