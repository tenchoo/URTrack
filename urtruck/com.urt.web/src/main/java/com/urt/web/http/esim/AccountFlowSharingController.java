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
 * device+ 加入levonoID流量共享组,返回成功/失败
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/esimAddGroup")
public class AccountFlowSharingController {
	
	private static final Logger log=Logger.getLogger(AccountFlowSharingController.class);
	@Autowired
	private ESIMService esimService;
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value = "addGroup", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JSONObject flowShare(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "imei", required = true) String imei,
			@RequestParam(value = "st", required = true) String st
			) throws Exception {
		log.info("enter the method flowShare");
		JSONObject resultJson=new JSONObject();
		String retcode="1";
		if(StringUtil.isBlank(imei)&&StringUtil.isBlank(st)){
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
		String levonoId = account.getAccountID();
		log.info("levonoId"+levonoId);
		levonoId = account.getUsername();	
		log.info("mobileNumber"+levonoId);
		boolean issuccess = esimService.addGroup(levonoId, imei);
		if(!issuccess){
			retcode="-2";
		}
		resultJson.put("retcode", retcode);
		log.info("exit the method flowShare");
		return resultJson;
	}
	
	
}
