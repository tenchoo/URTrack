package com.urt.web.http.device;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.http.device.DeviceProductService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 缴费查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/paymentquery")
public class PaymentQueryController {
	
	private static final Logger log=Logger.getLogger(PaymentQueryController.class);
	private static final String SERVERNAME="device+缴费查询";
	
	@Autowired
	private DeviceProductService deviceService;
	@Autowired
	private ServerCheckService serverService;
	
	@RequestMapping(value = "paymentquery", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JSONObject PaymentQuery(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "orderID", required = true) String orderId
			) throws Exception {
		log.info("enter the method PaymentQuery");
		JSONObject resultJson=new JSONObject();
		Map<String,String> reqInfo=new HashMap<String, String>();
		reqInfo.put("iccid", orderId);
		String retCode = "1";//-1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid -10 未支付
		try {
			if(StringUtil.isBlank(orderId)){
				retCode="-1";
			}else{
				JSONObject result = deviceService.selectTradeInfoByTradeId(Long.valueOf(orderId));
				String payTag=result.getString("payTag");
				if("0".equals(payTag)||StringUtil.isBlank(payTag)){
					retCode="-10";
				}
				resultJson.put("paymentMoney", result.getString("paymentMoney")==null?"":result.getString("paymentMoney"));
				resultJson.put("paymentTime", result.getString("paymentTime")==null?"":result.getString("paymentTime"));
				resultJson.put("goodsName", result.getString("goodsName")==null?"":result.getString("goodsName"));
			}
		} catch (Exception e) {
			retCode="-4";
		}
		resultJson.put("retCode", retCode);
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("orderID", orderId);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method PaymentQuery");
		return resultJson;
	}
	
	
}
