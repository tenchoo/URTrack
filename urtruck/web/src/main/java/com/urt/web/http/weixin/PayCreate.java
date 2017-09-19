package com.urt.web.http.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.urt.web.util.WeixinPayConstants;
import com.urt.web.util.WeixinUtil;

@Controller
@RequestMapping("/PayCreate")
public class PayCreate {
	  private static final Logger log = Logger.getLogger(PayNotifyCallBack.class);
	  //private static  final  String  WeixinPayUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";
	  //"http://h5mobiletest.lenovomm.com/PayNotifyCallBack/payCallBack";
	  
	  @Value("#{configProperties['device.WeixinPayUrl']}")
	  private String  WeixinPayUrl;
	  //通知地址
	  @Value("#{configProperties['device.WeixinPay.notify_url']}")
	  private String notify_url; 
	  //交易类型
	  private static final String trade_type = "NATIVE";  
	  
	  @RequestMapping(value = "PayCreate", method = { RequestMethod.POST, RequestMethod.GET})
	  public ModelAndView PayCreate(HttpServletRequest request, HttpServletResponse response){
		    //参数拼装http://localhost
	        //调用微信下单接口 进行预支付处理
		     String orderPrice = request.getParameter("order_price"); //价格   注意：价格的单位是分  
		     String body =request.getParameter("body");   // 商品名称  
		     String out_trade_no = request.getParameter("out_trade_no"); // 订单号  
			try {
				SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();  
				packageParams.put("appid", WeixinPayConstants.appid);  
				packageParams.put("mch_id", WeixinPayConstants.mch_id);  
				packageParams.put("nonce_str", WeixinUtil.getRandomString(32));  
				packageParams.put("body", body);  
				packageParams.put("out_trade_no", out_trade_no);  
				packageParams.put("total_fee", orderPrice);  
				String ip=WeixinUtil.getIpAddr(request);
//				ip = "172.17.8.248";
				log.info(">>>>>>>>>>>>>>ip++++notify_url"+ip+"<<<<<<<<<<<>>>>>>>>"+notify_url);
				packageParams.put("spbill_create_ip", ip);  
				packageParams.put("notify_url", notify_url);  
				packageParams.put("trade_type", trade_type);
				Date date = new Date();
				packageParams.put("time_start",toDate(date));
				packageParams.put("time_expire",toDate1(date));
				//进行加密处理
				String createSign = PayCommonUtil.createSign("UTF-8", packageParams, WeixinPayConstants.api_key);
				packageParams.put("sign", createSign);
				//转化请求参数为XML格式
				String requestXml = PayCommonUtil.getRequestXml(packageParams);
			    String	postData = HttpUtil.postData(WeixinPayUrl, requestXml);
			    if (postData==null) {
			    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>请求微信预支付结果为空<<<<<<<<<<<<<<<<<<<<");
			    	return null;
				}
			    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>微信预支付结果<<<<<<<<<<<<<<<<<<<<"+postData);
				Map map = XMLUtil.doXMLParse(postData);
				String code_url = (String) map.get("code_url");
				ModelAndView mv = new ModelAndView("device/devicepay");
				if (code_url==null) {
					log.info("微信预支付接口异常>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					mv.addObject("orderPrice", Double.valueOf(orderPrice)/100);
					mv.addObject("outTradeNo",out_trade_no);
				}else{
					mv.addObject("outTradeNo",out_trade_no);
					mv.addObject("orderPrice", Double.valueOf(orderPrice)/100);
					mv.addObject("codeUrl",code_url);
					mv.addObject("token", "2");
				}
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("微信预支付接口异常>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			} 
		  return null;
	  }
	  
	  public  static String toDate(Date date){
		  SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		  String format = df.format(date);
		  return format;
	  }
	  public  static String toDate1(Date date){
		  long time = date.getTime()+5*60*1000;
		  Date date2 = new Date(time);
		  SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		  String format = df.format(date2);
		  return format;
	  }
    
}
