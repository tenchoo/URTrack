package com.urt.web.http.device;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.unicom.PayBackDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.http.device.DeviceProductService;
/**
 * device+ pad端支付回调
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/back")
public class PayCallBackController {
	
	private static final Logger log=Logger.getLogger(PayCallBackController.class);
	private static final String SERVERNAME="device+ 支付回调";
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private DeviceProductService deviceService;
	@Autowired
	private ServerCheckService serverService;
	
	@RequestMapping(value = "payBack", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String payBack(
			HttpServletRequest request,
			HttpServletResponse response
//			HttpSession session,
//			PayBackDto backDto
			) throws Exception {
		log.info(">>>>>>>>>>>>>>>enter the method device+ payBack<<<<<<<<<<<<<<<");
		String issuccess="success";
//		if (backDto==null) {
//			issuccess="fail";
//			return issuccess;
//		}
//		JSONObject json=new JSONObject();
		PayBackDto backDto=new PayBackDto();
//		//获取请求参数
//		try {
//			InputStream _input = request.getInputStream();
//			InputStreamReader _reader = new InputStreamReader(_input);
//			BufferedReader _buff = new BufferedReader(_reader);
//			StringBuffer _sb = new StringBuffer();
//			String _b = null;
//			while ((_b = _buff.readLine()) != null) {
//				_sb.append(_b + System.getProperty("line.separator"));
//			}
//			System.out.println(("接收到的值：" + _sb));
//			log.info(">>>>>>>>>>>>>>>enter the method device+ payBack param"+_sb.toString()+" <<<<<<<<<<<<<<<");	
//			json=JSONObject.parseObject(_sb.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.info(">>>>>>>>>>>>>>>enter the method device+ payBack Exception <<<<<<<<<<<<<<<");	
//			issuccess="fail";
//			return issuccess;
//		}
//		if(json!=null){
//			backDto=paramReqUtil(request,backDto);
//		}else{
//			log.info(">>>>>>>>>>>>>>>enter the method device+ payBack Exception <<<<<<<<<<<<<<<");	
//			issuccess="fail";
//			return issuccess;
//		}
		backDto=paramReqUtil(request,backDto);
		System.out.println(("接收到的值：" + backDto.toString()));
		try {
			Boolean flag=true;
			String orderId = backDto.getMerchantOrderId();
			TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(orderId));
			if(tradeDto != null){
				Map<String, Object> result = deviceService.payCallBacktMap(backDto);
				flag = (Boolean) result.get("recode");
				if(!flag)
					issuccess="fail";
			}else{
//			Map<String, Object> result=deviceService.payCallBack(backDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(">>>>>>>>>>>>>>>enter the method device+ payBack Exception <<<<<<<<<<<<<<<");	
			issuccess="fail";
			return issuccess;
		}
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
		int orderStatus = backDto.getOrderStatus();
		if(orderStatus==1){
			recordDto.setIsSuccess("0");
		}else{
			recordDto.setIsSuccess("1");
		}
//		JSONObject reqJson=new JSONObject();
		recordDto.setReqInfo(backDto.toString());
		recordDto.setRspInfo(issuccess);
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info(">>>>>>>>>>>exit the method device+ payBack"+issuccess+"<<<<<<<<<<<<<<<<<<<");
		
		response.getWriter().write(issuccess);
		return null;
	}

	private PayBackDto paramReqUtil(HttpServletRequest request,
			PayBackDto backDto) throws UnsupportedEncodingException {
		backDto.setPayAmount(Integer.parseInt((String) request.getParameter("payAmount")));
		String privateAttach = (String) request.getParameter("privateAttach");
		String charset=(String) request.getParameter("_input_charset");
		backDto.setPrivateAttach(URLDecoder.decode(privateAttach,charset));
//		backDto.setPrivateAttach(privateAttach);
		backDto.setTradeNo((String) request.getParameter("tradeNo"));
		backDto.setSign_type((String) request.getParameter("sign_type"));
		backDto.setOrderTime((String) request.getParameter("orderTime"));
		backDto.setOrderStatus(Integer.parseInt((String) request.getParameter("orderStatus")));
		backDto.setSign((String) request.getParameter("sign"));
		backDto.set_input_charset(charset);
		backDto.setUserId((String) request.getParameter("userId"));
		backDto.setPayType(Integer.parseInt((String) request.getParameter("payType")));
		backDto.setUserName((String) request.getParameter("userName"));
		backDto.setBankOrderid((String) request.getParameter("bankOrderid"));
		backDto.setMerchantOrderId((String) request.getParameter("merchantOrderId"));
		return backDto;
	}

	
	
}
