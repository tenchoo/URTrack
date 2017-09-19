package com.urt.web.http.maichi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.http.maichi.FlowPayService;
import com.urt.web.http.recevice.FlowOrderController;

/**
 * 第三方充值流量是调用的接口
 * @author 
 *
 */
@Controller
@RequestMapping("/flowPay")
public class FlowPayController {
	 
	private static  Logger log=Logger.getLogger(FlowPayController.class);
	@Resource
	private  FlowPayService   flowPayService;
	
	
	 @RequestMapping(value = "flowOrder", method = { RequestMethod.POST,RequestMethod.GET})
	 public  @ResponseBody JSONObject flowPay(HttpServletResponse response,HttpServletRequest  request ){
		 
		JSONObject requestJson = new JSONObject();
		JSONObject respJson = new JSONObject();
		try {
			ServletInputStream inputStream = request.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);			
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String str=null;
		    while((str=bufferedReader.readLine())!=null){
		    	sb.append(str + System.getProperty("line.separator"));
		    	
		    }
		    requestJson=JSONObject.parseObject(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
			respJson.put("RESP_CODE", "1111");
		    respJson.put("RESP_DESC", "处理异常");
		    log.info("exit the method flowOrder");
		    return respJson;
		}
		if (requestJson==null) {
		     requestJson=new JSONObject();
		     System.out.println(">>>>>>没有接收到充值下单参数");
		     respJson.put("RESP_CODE", "29");
		     respJson.put("RESP_DESC", "订单提交失败");
			 log.info("exit the method flowOrder");
			 return respJson;
		}
		 //获取请求的参数并进行校验
		 try {
			String mobile=requestJson.getString("mobiles"); 
			 String transId=requestJson.getString("clientOrderId");
			 String productId=requestJson.getString("packageSize");  //产品编号
			 String appId=requestJson.getString("appId");          //2为流量，3为话费
			 String appkey=requestJson.getString("appKey");
			 String custId=requestJson.getString("custId");
			 respJson = flowPayService.paramCheck(mobile,transId,productId,appId,appkey,custId);
		  } catch (Exception e) {
			 e.printStackTrace();
			 respJson.put("RESP_CODE", "01");
		     respJson.put("RESP_DESC", "缺少必要的参数");
			 log.info("exit the method flowOrder");
			 return respJson;
		  }
		 log.info("exit the method flowOrder");
		 return respJson;
	 }
}
