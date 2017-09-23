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
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.ServerCheckService;

@Controller
@RequestMapping("/deviceFlowQuery")
public class DeviceFlowQuery {
	private static final String SERVERNAME="移动/电信流量包查询接口";
	private static final Logger log = Logger.getLogger(QueryGoodsController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ServerCheckService serverService;
	@RequestMapping(value = "flowQuery", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,String> flowQuery(HttpServletRequest request, HttpServletResponse response) {
		String iccid = request.getParameter("iccid").replace("#", "B");;
		Date date=new Date();
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		
		Map<String, String> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
		try{
			if(StringUtils.isEmpty(iccid)){
				resultMap.put("retcode", "-1");//参数不全
				log.info("参数不全！！！");
			}else{
				Map<String,String> planResult = deviceService.getCurentPlan(iccid);
				if(null != planResult){
					resultMap.put("surplusCapacityFlow", planResult.get("surplusCapacityFlow"));//当月剩余总流量
					resultMap.put("expirationDate", planResult.get("expirationDate"));//月套餐过期时间
					resultMap.put("flowBaseSize", planResult.get("flowBaseSize"));//基础套餐每月流量
					resultMap.put("availableFlow", planResult.get("availableFlow"));//当月可用总流量
					resultMap.put("usedFlow", planResult.get("usedFlow"));//当月已使用流量
					resultMap.put("beforeflowDate", planResult.get("beforeflowDate"));
					resultMap.put("retcode", "1");
				}else{
					resultMap.put("retcode", "-4");
				}
				recordDto.setCustId(userInfo.getCustId());
			}		
		}catch (Exception e){
			resultMap.put("retcode", "-4");//系统异常
			log.info("系统异常");
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
