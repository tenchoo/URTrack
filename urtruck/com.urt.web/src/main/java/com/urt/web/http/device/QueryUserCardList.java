package com.urt.web.http.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.ServerCheckService;

@Controller
@RequestMapping("/queryCard")
public class QueryUserCardList {
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private ServerCheckService serverService;
	
	private static final String SERVERNAME="用户下卡数量";
	private static final Logger log = Logger.getLogger(QueryUserCardList.class);
	
	@RequestMapping(value = "cardList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void cardList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long beginTime = System.currentTimeMillis();
		log.info("用户下卡数量：query card list BeginTime: " + beginTime);
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");


		JSONObject reqJson=new JSONObject();
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		List<Map<String, Object>> li = new ArrayList<Map<String,Object>>();
		PrintWriter out = response.getWriter();
		JSONObject resultJson=new JSONObject();
		String retcode="";
		try{
		
			Account account = deviceService.authSt(st);
			log.info(">>>>St>>>"+st+"<<<<<<<<<<<<lenovoId>>>>>>>"+account.getUsername());
			if ( StringUtils.isBlank(st)) {
				retcode="-1";// 参数不全
				log.info("参数不全！！！");
			}/*else if(account == null){
				retcode="-2";// st校验失败
				log.info("*****");
			}*/else{
				 //li = deviceService.queryIccidList(account.getAccountID());
				li = deviceService.queryIccidList("1007341111");
				 
					retcode="1";		
			}
			 
			 
		}catch (Exception e){
			retcode="-4";//系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		resultJson.put("iccidInfo", li);
		resultJson.put("retcode", retcode);
		out.println(resultJson.toString());
		if("1".equals(resultMap.get("retcode"))){
			recordDto.setIsSuccess("0");
		}else{
			recordDto.setIsSuccess("1");
		}	
		recordDto.setErrorCode(retcode);	
		recordDto.setRspInfo(resultJson.toJSONString());
		recordDto.setIpAddress(request.getRemoteHost());
		recordDto.setUserName("");
		recordDto.setServerName(SERVERNAME);	
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		recordDto.setReqInfo(reqJson.toString());
		serverService.savaLogerToDb(recordDto);	
		
	}
}
