package com.urt.web.http.device;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.http.device.DeviceProductService;

/**
 * device+ 发现服务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/find")
public class FindServerController {
	
	private static final Logger log=Logger.getLogger(FindServerController.class);
	private static final String SERVERNAME="device+发现服务";
	
	@Autowired
	private DeviceProductService deviceProductService;
	@Autowired
	private ServerCheckService serverService;
	
	@RequestMapping(value = "findserver", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JSONObject findserver(HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("enter the method findserver");
		//String tag = request.getParameter("languageTag");
		//String tag="1";//广告类型
		String tag = request.getParameter("languageTag");
		if ("zh-CN".equals(tag)) {
			tag="zh-CN";
		}else{
			tag="en";
		}
		JSONObject resultJson=new JSONObject();
		String retCode = "1";//-1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
		try {
			 List<Map<String, Object>> info = deviceProductService.selectShowAds(tag);
			resultJson.put("info", info);
			if(info.size()<=0){
				retCode="-6";
			}
		} catch (Exception e) {
			retCode="-4";
		}
		resultJson.put("retCode", retCode);
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("date", new Date());
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString().length()>2000 ? resultJson.toString().substring(0, 2000):resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method findserver");
		return resultJson;
	}
	
	
}
