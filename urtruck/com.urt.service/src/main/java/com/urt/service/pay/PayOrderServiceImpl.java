package com.urt.service.pay;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.pay.PayOrderService;
import com.urt.utils.HttpPostSend;
@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {
	@Value("${device.padCreate}")
	private String CREATEORDER;
	//private static final String CREATEORDER = "http://vbtest.lenovomm.cn/generalCashier/create_order.xhtml";// 支付测试地址
	private static final Logger log=Logger.getLogger(PayOrderServiceImpl.class);
	@Override
	public JSONObject createOrder(JSONObject paramJson) {
		
		JSONObject requestParam = new JSONObject();
		requestParam.put("body",paramJson);
		System.out.println(requestParam.toString());
		HttpPostSend httpPostSend = new HttpPostSend();
		String invoke = httpPostSend.invoke(requestParam.toString(), CREATEORDER);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>调用SDk返回值>>>>>>>>"+invoke);
		JSONObject jsonObject = new JSONObject();
		JSONObject parseObject = jsonObject.parseObject(invoke);
		Integer resultCode = (Integer) parseObject.get("resultCode");
		String tradeNo = null;
		String sign = null;
		String errorUrl=null;
		String errorMsg = ""; // 0创建订单成功  1创建订单失败
		if ("0".equals(resultCode.toString())) {
			tradeNo = (String) parseObject.get("tradeNo");
			sign = (String) parseObject.get("sign");
		} else {
			errorMsg = (String) parseObject.get("errorMsg");
			errorUrl=(String) parseObject.get("errorUrl");
		}
		jsonObject.put("tradeNo", tradeNo);
		jsonObject.put("errorMsg", errorMsg);
		jsonObject.put("sign", sign);
		jsonObject.put("resultCode", resultCode.toString());
		jsonObject.put("errorUrl", errorUrl);
		log.info("enter the method createOrder");
		return jsonObject;
	}
}
