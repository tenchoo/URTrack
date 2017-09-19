package com.urt.web.http.esim;

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
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.ESIMService;
import com.urt.web.common.util.StringUtil;

/**
 * 
 * @author wangxb20
 *流量转增接口
 */
@Controller
@RequestMapping("/esimGivenFlow")
public class GivenFlow {
	private static final Logger log=Logger.getLogger(GivenFlow.class);
	@Autowired
	private ESIMService esimService;
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value = "given", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JSONObject givenFlow(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "bGivenLenovoID", required = true) String bGivenLenovoID,
			@RequestParam(value = "operator", required = true) String operator,
			@RequestParam(value = "flowSize", required = true) String flowSize,
			@RequestParam(value = "st", required = true) String st
			) throws Exception {
		log.info("enter the method givenFlow");
		JSONObject resultJson=new JSONObject();
		String retcode="1";
		if(StringUtil.isBlank(bGivenLenovoID)&&StringUtil.isBlank(st)&&StringUtil.isBlank(operator)&&StringUtil.isBlank(flowSize)){
			retcode="-2";
			resultJson.put("retcode", retcode);
			return resultJson;
		}
		Account account = deviceService.authSt(st);
		if(account==null){
			retcode="-2";
			resultJson.put("retcode", retcode);
			return resultJson;
		}
		String givenLenovoIDlevonoId = account.getAccountID();
		log.info("levonoId"+givenLenovoIDlevonoId);
		givenLenovoIDlevonoId = account.getUsername();	
		log.info("mobileNumber"+givenLenovoIDlevonoId);
		//String givenLenovoIDlevonoId = "10070840727";
		try{
			String result = esimService.givenFlow(givenLenovoIDlevonoId, bGivenLenovoID, operator, flowSize);
			if("-5".equals(result)){
				retcode="-5";
			}else if("-6".equals(result)){
				retcode = "-6";
			}
		}catch(Exception e){
			retcode="-4";
			e.printStackTrace();
		}
		
		resultJson.put("retcode", retcode);
		log.info("exit the method givenFlow");
		return resultJson;
	}
}
