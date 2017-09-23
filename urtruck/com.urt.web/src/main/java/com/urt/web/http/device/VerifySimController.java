package com.urt.web.http.device;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.device.Sha256;
@Controller
@RequestMapping("/deviceVerifySim")
public class VerifySimController {
	private static final String SERVERNAME="校验SIM卡";
	private static final Logger log = Logger.getLogger(QueryGoodsController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ServerCheckService serverService;
	@RequestMapping(value = "verifySim", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,String> verifySim(HttpServletRequest request, HttpServletResponse response) {
		String iccid = request.getParameter("iccid").replace("#", "B");;
		String st = request.getParameter("st");
		String imei = request.getParameter("imei");
		Date date = new Date();
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("imei", imei);
		Map<String, String> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
//		LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
		Account account = deviceService.authSt(st);
		log.info(">>>>St>>>"+st);
		log.info("<<<<<<<<<<<<lenovoId>>>>>>>"+account+">>>>>ICCID>>>>>>>"+iccid);
		log.info("imei=================="+imei);
		try{
			if (StringUtils.isEmpty(iccid) || StringUtils.isBlank(st) || StringUtils.isBlank(imei)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
			}else if(account == null){
				resultMap.put("retcode", "-2");// st校验失败
				log.info("*****");
			}else{
				IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
				boolean falg  = deviceService.findImeiLibraryByIccid(imei);
				if (!falg) {
					falg=deviceService.insertImeiLibrary(imei);
				}
				boolean iccidRes = iccidLibrary!=null?true:false;
				boolean imeiRes = falg;
				if(iccidRes && imeiRes) {
					resultMap.put("retcode", "1");
					if("1".equals(iccidLibrary.getOperators())){
						resultMap.put("cardType", "1");
					}else if("2".equals(iccidLibrary.getOperators())){
						resultMap.put("cardType", "2");
					}else if("3".equals(iccidLibrary.getOperators())){
						resultMap.put("cardType", "3");
					}
					
				}else {
					resultMap.put("retcode", "-3");
					if(!iccidRes){
						log.info("----------非法iccid---------");
					}
					if(!imeiRes){
						log.info("---------非法imei----------");
					}
				}
				if (iccidLibrary==null) {
					resultMap.put("retcode", "-3");
					log.info("----------非法iccid---------");
				}else{
					recordDto.setCustId(Long.parseLong(iccidLibrary.getCustid()));
				}
			}
		}catch(Exception e){
			resultMap.put("retcode", "-4");
			e.printStackTrace();
		}	
		if("1".equals(resultMap.get("retcode"))){
			recordDto.setIsSuccess("0");
		}else{
			recordDto.setIsSuccess("1");
		}	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordDto.setErrorCode(resultMap.get("retcode").toString());	
		recordDto.setRspInfo(new JSONObject().toJSONString(resultMap));
		recordDto.setIpAddress(request.getRemoteHost());
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);	
		recordDto.setAccessDate(date);
		recordDto.setParaName1("device");
		recordDto.setParaName2(sdf.format(new Date()));
		recordDto.setReqInfo(reqJson.toString());
		serverService.savaLogerToDb(recordDto);	
		
		return resultMap;
		
	}
}
