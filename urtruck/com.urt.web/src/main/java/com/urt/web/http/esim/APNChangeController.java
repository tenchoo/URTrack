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
 * device+ 鍒囨崲apn
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/esimOperatorChange")
public class APNChangeController {
	
	private static final Logger log=Logger.getLogger(APNChangeController.class);
	@Autowired
	private ESIMService esimService;
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value = "operatorChange", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JSONObject goodsChanging(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "imei", required = true) String imei,
			@RequestParam(value = "st", required = true) String st,
			@RequestParam(value = "operator", required = true) String operator
			) throws Exception {
		log.info("enter the method goodsChanging");
		JSONObject resultJson=new JSONObject();
		String retcode="1";
		if(StringUtil.isBlank(imei)&&StringUtil.isBlank(st)&&StringUtil.isBlank(operator)){
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
		try{
			boolean isOk = esimService.changeApn(levonoId, imei,operator);
			log.info("++++++++++++++++++++++++"+isOk);
//			boolean isOk=true;
			if(!isOk){
				log.info("++++++++++++++++++++++++ -1");
				retcode="-1";//杩愯惀鍟嗗拰鍦板煙涓嶅尮閰�
			}
				
		}catch(Exception e){
			retcode="-4";
			e.printStackTrace();
		}
		log.info("exit the method goodsChanging");
		resultJson.put("retcode", retcode);
		return resultJson;
	}
	
	
}
