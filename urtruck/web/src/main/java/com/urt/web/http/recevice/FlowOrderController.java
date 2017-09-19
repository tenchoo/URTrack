package com.urt.web.http.recevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.flow.FlowOrderService;
import com.urt.interfaces.http.maichi.FlowPayService;

/**
 * 第三方订购流量时调用的服务
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/flow")
public class FlowOrderController {

	private static Logger log = Logger.getLogger(FlowOrderController.class);

	@Autowired
	private FlowOrderService flowOrderService;

	/*
	 * @Resource private FlowPayService flowPayService;
	 */
	/**
	 * 第三方订购流量时调用接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "flowOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject flowOrder(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		log.info("enter the method flowOrder");
		// 流量订购第一步
		JSONObject reqJson = new JSONObject();
		JSONObject respObj = new JSONObject();
		try {
			InputStream _input = request.getInputStream();
			InputStreamReader _reader = new InputStreamReader(_input);
			BufferedReader _buff = new BufferedReader(_reader);
			StringBuffer _sb = new StringBuffer();
			String _b = null;
			while ((_b = _buff.readLine()) != null) {
				_sb.append(_b + System.getProperty("line.separator"));
			}
			System.out.println(("接收到的值：" + _sb));
			reqJson = JSONObject.parseObject(_sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
			respObj.put("RESP_CODE", "29");
			respObj.put("RESP_DESC", "订单提交失败");
			respObj.put("failPhones", "");
			respObj.put("clientOrderId", "");
			flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
			log.info("exit the method flowOrder");
			return respObj;
		}

		if (reqJson == null) {
			reqJson = new JSONObject();
			System.out.println(">>>>>>没有接收到下单参数");
			respObj.put("RESP_CODE", "29");
			respObj.put("RESP_DESC", "订单提交失败");
			respObj.put("failPhones", "");
			respObj.put("clientOrderId", "");
			flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
			log.info("exit the method flowOrder");
			return respObj;
		}
		// {"custId":"3071458290028741","appKey":"4q31joU71n30V4ni9cmjy65U486u29","packageSize":"10","mobile":"13522824721","clientOrderId":"123456789"}
		String custId = reqJson.getString("custId");
		String appkey = reqJson.getString("appkey");
		String clientOrderId = reqJson.getString("clientOrderId");
		String mobile = reqJson.getString("mobile");
		String packageSize = reqJson.getString("packageSize");
		log.info("custId=" + custId + ",appkey=" + appkey + ",clientOrderId=" + clientOrderId + ",mobile=" + mobile
				+ ",packageSize");
		// 校验请求参数
		respObj = flowOrderService.paramCheck(custId, appkey, clientOrderId, mobile, packageSize);

		try {
		} catch (Exception e) {
			e.printStackTrace();
			respObj.put("RESP_CODE", "29");
			respObj.put("RESP_DESC", "订单提交失败");
			respObj.put("failPhones", "");
			respObj.put("clientOrderId", "");
			flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
			log.info(">>>>>>>>响应给调用者的结果"+respObj.toString());
			return respObj;
		}
		if ("41".equals(respObj.get("RESP_CODE"))) {
			flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
			log.info(">>>>>>>>响应给调用者的结果"+respObj.toString());
			return respObj;
		} else {
			// TODO第一步初始化订单,订单日志表 可以单开一个线程
			if (!"00".equals(respObj.get("RESP_CODE"))) {
				flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
				log.info(">>>>>>>>响应给调用者的结果"+respObj.toString());
				return respObj;
			} else {
				// TODO流量订购第二步判断校验是否成功,然后发送请求，返回大汉三通相应结果，并在数据库进行记录
				String flowOrderId = flowOrderService.insertFlowOrderAndLog(reqJson, respObj);
				JSONObject resultJson = flowOrderService.sentPostToDaHan(reqJson, flowOrderId);
				respObj.put("RESP_CODE", resultJson.getString("resultCode"));
				respObj.put("RESP_DESC", resultJson.getString("resultMsg"));
				respObj.put("failPhones", resultJson.getString("failPhones"));
				respObj.put("clientOrderId", resultJson.getString("clientOrderId"));
				log.info(">>>>>>>>响应给调用者的结果"+respObj.toString());
				return respObj;
			}
		}

	}

}
