package com.urt.web.http.device;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.http.DayFlowQuery;
import com.urt.dto.http.FlowInfo;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.FlowQueryServer;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 月流量查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/monthflowquery")
public class MonthFlowQueryController {
	
	private static final Logger log=Logger.getLogger(MonthFlowQueryController.class);
	private static final String SERVERNAME="device+月流量查询";
	
	@Autowired
	private FlowQueryServer flowQueryService;
	@Autowired
	private UserService userService;
	@Autowired
	private ServerCheckService serverService;
	
	
	@RequestMapping(value = "monthflowquery", method = { RequestMethod.POST,RequestMethod.GET})
	public void monthFlowQuery(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "date", required = true) String date
			) throws Exception {
		iccid = iccid.replace("#", "B");
		log.info("enter the method monthflowquery");
		PrintWriter out = response.getWriter();
		JSONObject resultJson=new JSONObject();
		Map<String,String> reqInfo=new HashMap<String, String>();
		reqInfo.put("iccid", iccid);
		reqInfo.put("dayDate", date);
		String retCode = "1";//-1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
		String custId="";
		try {
			if(StringUtil.isBlank(iccid)||StringUtil.isBlank(date)){
				retCode="-1";
			}else{
				 DayFlowQuery details = flowQueryService.monthFlowQuery(reqInfo);
				 if("0001".equals(details.getRespCode())){
					 retCode="-4";
				 }else{
					 List<FlowInfo> flowInfos = details.getFlowInfo();
					 if(flowInfos==null){
						 retCode="-6";
					 }else{
						 double totalFlowSize=0;
						 for (FlowInfo flowInfo : flowInfos) {
							 String flowSize = flowInfo.getFlowSize();
							 double parseLong = Double.parseDouble(flowSize);
							 totalFlowSize+=parseLong;
						 }
						 if (totalFlowSize==0.0) {
							 flowInfos=new ArrayList<>();
						 }
					 }
					 resultJson.put("flowInfo", flowInfos);
				 }
			}
		} catch (Exception e) {
			retCode="-4";
		}
		resultJson.put("retCode", retCode);
		resultJson.put("iccid", iccid);
		out.println(resultJson.toString());
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		custId = userService.getCustIdByIccid(iccid);
		if(!StringUtil.isBlank(custId)){
			recordDto.setCustId(Long.valueOf(custId));
		}
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("date", date);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method monthflowquery");
	}
	
	
}
