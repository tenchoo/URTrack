package com.urt.web.dmp;

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

import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.http.StopOnDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.CardActiveService;
/**
 * dmp用户服务处理
 * @author zss
 * @date 2017年03月17日
 */
@Controller
@RequestMapping("/DMPServiceController")
public class DMPUserServeController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private CardActiveService cardActiveService;
	//停开机
	@RequestMapping(value = "stopOrOnCard", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> stopOrONCard(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "channelCustId", required = true) String channelCustId,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "dmpkey", required = true) String dmpkey,
			@RequestParam(value = "stateCode", required = true) String stateCode//1停卡操作，2开卡操作
			){
		log.info("进入停开卡接口DMPServiceController");
		log.info("channelCustId------:"+channelCustId);
		log.info("iccid------:"+iccid);
		log.info("dmpkey------:"+dmpkey);
		log.info("stateCode--------:"+stateCode);
		Map<String, Object> resultMap = new HashMap<>();
		try{
		IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
		if(iccidLibrary==null){
			resultMap.put("retcode", "-7");
			resultMap.put("respDesc","此卡未注册");
			log.info("此卡未注册:"+iccid);
			return resultMap;
		}
		String custId = iccidLibrary.getCustid();
		if(custId==null||"".equals(custId)){
			resultMap.put("retcode", "-6");
			resultMap.put("respDesc","卡与用户未绑定");
			log.info("卡与用户未绑定");
			return resultMap;
		}
		// 激活操作
		Map<String, String> reqInfo = new HashMap<String, String>();
		reqInfo.put("iccid", iccid);
		reqInfo.put("custId", custId);
		reqInfo.put("ifMaintenance", "0");
		reqInfo.put("tradeTypeCode", "110");
		CardStatusDto queryCardStatus = cardActiveService.queryCardStatus(reqInfo);
		String cardStatus = queryCardStatus.getCardStatus();//1开卡状态  2停卡状态
		if("1".equals(cardStatus)){
			if("2".equals(stateCode)){
				resultMap.put("retcode", "3");
				resultMap.put("respDesc","此卡已经是开卡状态");
				log.info("用户重复开卡");
				return resultMap;
			}
		}
		if("2".equals(cardStatus)){
			if("1".equals(stateCode)){
				resultMap.put("retcode", "4");
				resultMap.put("respDesc","此卡已经是停卡状态");
				log.info("用户重复停卡卡");
				return resultMap;
			}
		}
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("stateCode", stateCode);//1停卡 2开卡
		hashMap.put("iccid", iccid);
		hashMap.put("custId", custId);
		StopOnDto stopOn = cardActiveService.stopOn(hashMap);
		if ("0".equals(stopOn.getIsSuccess())) {
			resultMap.put("retcode", "1");     //1停开机正常
			resultMap.put("iccid", iccid);
			resultMap.put("respDesc",stopOn.getRespDesc());
		} else {
			resultMap.put("retcode", "0");     //停开机异常
			resultMap.put("respDesc",stopOn.getRespDesc());
		}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("retcode", "-4");
			resultMap.put("respDesc","系统异常");
			log.info("系统异常");
			return resultMap;
		}
		log.info("退出停开卡接口DMPServiceController");
		return resultMap;
	}
	
}
