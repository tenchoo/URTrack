package com.urt.web.http.device;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.http.DayFlowQuery;
import com.urt.dto.http.FlowInfo;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.FlowQueryServer;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 日流量查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dayflowquery")
public class DayFlowQueryController {
	
	private static final Logger log=Logger.getLogger(DayFlowQueryController.class);
	private static final String SERVERNAME="device+日流量查询";
	
	@Autowired
	private FlowQueryServer flowQueryService;
	@Autowired
	private UserService userService;
	@Autowired
	private ServerCheckService serverService;
	
	
	@RequestMapping(value = "dayflowquery", method = { RequestMethod.POST,RequestMethod.GET})
	public void dayFlowQuery(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "date", required = false) String date
			) throws Exception {
		log.info("enter the method dayFlowQuery");
		iccid = iccid.replace("#", "B");
		Date date2 = new Date();
		if(StringUtil.isBlank(date)){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			date=format.format(new Date());
		}
		PrintWriter out = response.getWriter();
		JSONObject resultJson=new JSONObject();
		Map<String,String> reqInfo=new HashMap<String, String>();
		reqInfo.put("iccid", iccid);
		reqInfo.put("dayDate", date);
		String retCode = "1";//-1 参数不全 -2校验失败 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
		String custId="";
		try {
			if(StringUtil.isBlank(iccid)||StringUtil.isBlank(date)){
				retCode="-1";
			}else{
				 DayFlowQuery details = flowQueryService.dayFlowQuery(reqInfo);
				 if("0001".equals(details.getRespCode())){
					 retCode="-4";
				 }else{
					 FlowInfo flowInfo=new FlowInfo();
					 List<FlowInfo> flowInfos = details.getFlowInfo();
					 if(flowInfos==null){
						 retCode="-6";
					 }else{
						 flowInfo = flowInfos.get(flowInfos.size()-1);
					 }
					 resultJson.put("flowInfo", flowInfo);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("date", date);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(date2);
		recordDto.setParaName1("device");
		recordDto.setParaName2(sdf.format(new Date()));
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method dayFlowQuery");
	}
	
	
}
