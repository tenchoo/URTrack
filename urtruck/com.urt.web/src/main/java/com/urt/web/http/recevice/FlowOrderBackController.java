package com.urt.web.http.recevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
/**
 * 第三方订购流量时大汉三通回调的服务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/flowback")
public class FlowOrderBackController {
	
	private static  Logger log=Logger.getLogger(FlowOrderBackController.class);
	
	@Autowired
	private FlowOrderService flowOrderService;
	
	@RequestMapping(value = "OrderCallBack", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JSONObject callBackOrder(
			HttpServletRequest request,
			HttpServletResponse response
			) throws UnsupportedEncodingException {
		log.info("enter the method callBackOrder");
		System.out.println("》》》》》大汉三通开始回调懂得通信的下单回调接口");
		JSONObject reqJson=null;
		JSONObject respObj=new JSONObject();
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
			reqJson=JSONObject.parseObject(_sb.toString());

		} catch (IOException e) {
			respObj.put("resultCode", "1111");
			respObj.put("resultMsg", "处理失败");
			return respObj;
		}
		
		if(reqJson==null){
			respObj.put("resultCode", "1111");
			respObj.put("resultMsg", "处理失败");
			return respObj;
		}
		String clientOrderId=reqJson.getString("clientOrderId");
		String mobile=reqJson.getString("mobile");
		String reportTime=reqJson.getString("reportTime");
		String callBackTime=reqJson.getString("callBackTime");
		String status=reqJson.getString("status");
		String errorCode=reqJson.getString("errorCode");
		String errorDesc=reqJson.getString("errorDesc");
		String intervalTime=reqJson.getString("intervalTime");
		String clientSubmitTime=reqJson.getString("clientSubmitTime");
		String discount=reqJson.getString("discount");
		String costMoney=reqJson.getString("costMoney");
		String sign=reqJson.getString("sign");
		
		//流量订购第三步，大汉三通回调，状态报告
		respObj = flowOrderService.callBackCheck(clientOrderId,mobile,reportTime,callBackTime,status,errorCode,errorDesc,intervalTime,clientSubmitTime,discount,costMoney,sign);
		String flowOrderId=flowOrderService.getFlowOrderId(clientOrderId);
		JSONObject requestParams=new JSONObject();
		requestParams.put("clientOrderId", clientOrderId);
		requestParams.put("mobile", mobile);
		requestParams.put("reportTime", reportTime);//状态报告时间
		requestParams.put("callBackTime", callBackTime);//回调时间
		requestParams.put("status", status);//订购状态、0：订购成功、1：订购失败
		requestParams.put("errorCode", errorCode);//错误码
		requestParams.put("errorDesc", errorDesc);
		requestParams.put("intervalTime", intervalTime);//状态报告时间与客户提交订单的时间间隔
		requestParams.put("clientSubmitTime", clientSubmitTime);//客户提交订单时间
		requestParams.put("discount", discount);//折扣
		requestParams.put("costMoney", costMoney);//消费金额
		requestParams.put("sign", sign);//签名
		//根据客户端订单号查询查询客户的订单
		//大汉三通调用懂的通信后，到数据库记录
		flowOrderService.recordBackFlowLogThree(requestParams,respObj,flowOrderId);
		if(!"0000".equals(respObj.getString("resultCode"))){
			return respObj;
		}
		//流量第四步，懂得通信回调第三方系统，返回参数
		JSONObject resp = flowOrderService.sentPostToEdge(requestParams,flowOrderId);
		if(resp!=null){
			if("0000".equals(resp.getString("RESP_CODE"))){
				respObj.put("resultCode", "0000");
				respObj.put("resultMsg", "处理成功");
			}else{
				respObj.put("resultCode", "1111");
				respObj.put("resultMsg", "处理失败");
			}
		}else{
			respObj.put("resultCode", "1111");
			respObj.put("resultMsg", "处理失败");
		}
		System.out.println(">>>>发送返回值");
		System.out.println(">>>>返回值发送成功");
		System.out.println("》》》》》大汉三通回调懂得通信的下单回调接口完毕");
		log.info("exit the method callBackOrder");
		return respObj;
	}
}
