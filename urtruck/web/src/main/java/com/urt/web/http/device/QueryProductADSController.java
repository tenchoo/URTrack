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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.http.device.QueryProductADSService;

@Controller
@RequestMapping("/findProductADs")
public class QueryProductADSController {

	private static final Logger log = Logger.getLogger(QueryProductADSController.class);
	private static final String SERVERNAME = "device+广告位服务";

	
	@Autowired
	private ServerCheckService serverService;

	@Autowired
	private QueryProductADSService queryProductADSService;
    
	@RequestMapping("/queryList")
	public @ResponseBody JSONObject queryProductAD(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resultJson = new JSONObject();

		String resulCode = "0"; // 1正常 0代表没有可查询到的商品 -4 系统异常
		List<Map<String, Object>> queryProductADS = queryProductADSService.queryProductADS();
		try {
			if (null!=queryProductADS && queryProductADS.size() > 0) {
				resulCode = "1";
			}
		} catch (Exception e) {
			resulCode = "-4"; // 系统异常
		}
		resultJson.put("retcode", resulCode);
		resultJson.put("productAds", queryProductADS);
		// 保存日志
		LaoPeripheralSysAccessLogDto recordDto = new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
		if ("1".equals(resulCode)) {
			recordDto.setIsSuccess("1");
		} else {
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(resulCode);
		JSONObject reqJson = new JSONObject();
		reqJson.put("date", new Date());
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method queryProductAD");
		return resultJson;
	}
}
