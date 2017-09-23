package com.urt.web.http.esim;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.device.Account;
import com.urt.dto.esim.EsimAccountInfo;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.ESIMService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 鑾峰彇levonoID鐨勮处鎴锋祦閲忎俊鎭紝杩斿洖杩欎釜levonoID锛屼腑鍥芥祦閲忓寘鍓╀綑澶氬皯锛岃タ鐝墮娴侀噺鍖呭墿浣欏灏�
 * 鏈夊灏慖MEI璁惧锛岃澶囧瀷鍙锋槸浠�箞
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/esimFlow")
public class GetAccountFlowInfoController {
	
	private static final Logger log=Logger.getLogger(GetAccountFlowInfoController.class);
	@Autowired
	private ESIMService esimService;
	@Autowired
	private DeviceService deviceService;
	
	
	@RequestMapping(value = "queryFlowInfo", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public EsimAccountInfo getFlowInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "st", required = true) String st
			) throws Exception {
		log.info("enter the method getFlowInfo");
		EsimAccountInfo esimFlow=new EsimAccountInfo();
		esimFlow.setRetcode("1");
		if(StringUtil.isBlank(st)){
			esimFlow.setRetcode("-2");//閿欒缂栫爜(-2妫�獙澶辫触 -4绯荤粺寮傚父 1鎴愬姛)
			return esimFlow;
		}
		Account account = deviceService.authSt(st);
		if(account==null){
			esimFlow.setRetcode("-2");
			return esimFlow;
		}
		String levonoId = account.getAccountID();
		log.info("levonoId"+levonoId);
		levonoId = account.getUsername();	
		log.info("mobileNumber"+levonoId);
		esimFlow.setLenovoId(levonoId);
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		esimFlow.setCurentDate(df.format(d));
		esimFlow=esimService.getAccountInfo(esimFlow);
		log.info("exit the method getFlowInfo");
		return esimFlow;
	}
	
	
}
