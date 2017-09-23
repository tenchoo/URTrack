package com.urt.web.esim;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoEsimLogDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.EsimService2;

/**
 * 
 * Esim 连接
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("/esimConnect")
public class EsimConnectController {
	private static final Logger log = Logger.getLogger(EsimConnectController.class);

	private static final String tradeTypecode = "连接Service";

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private EsimService2 esimService;

	@RequestMapping(value = "connect", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object> connectMethod(HttpServletRequest request, HttpServletResponse response) {
		String iccid = request.getParameter("iccid");
		String eid = request.getParameter("eid");
		String lenovoId = request.getParameter("st");
		Map<String, Object> resultMap = new HashMap<>();

		LaoEsimLogDto esimLogDto = new LaoEsimLogDto();
		String respCode = "0000";
		boolean checkIccid=esimService.checkIccid(iccid);
		Account account = deviceService.authSt(lenovoId);
		if (StringUtils.isEmpty(lenovoId) || StringUtils.isBlank(eid) || StringUtils.isBlank(iccid)) {

			respCode = "0001";
			log.info("参数不全>>>>>>>>>>>>");
		}else if (null == account) {
			respCode = "0002";
			log.info("LenovoId校验不通过>>>>>>>>>>>>");
		}else if (!checkIccid) {
			respCode = "0006";
			log.info("iccid非法>>>>>>>>>>>>");
		}else {
			boolean connectServer = esimService.connectServer(account.getUsername(), eid, iccid);
			if (!connectServer) {
				respCode = "2001"; //连接失败
			}
		}
		resultMap.put("respCode", respCode);

		// 记录日志
		JSONObject requestJson = new JSONObject();
		requestJson.put("eid", eid);
		requestJson.put("lenovoId", lenovoId);
		requestJson.put("iccid", iccid);

		esimLogDto.setEid(eid);
		if (null != account) {
			esimLogDto.setLenovoid(account.getUsername());
		}
		esimLogDto.setRequestinfo(requestJson.toJSONString());
		esimLogDto.setIndate(new Date());
		esimLogDto.setTradeTypecode(tradeTypecode);
		esimLogDto.setRespcode(respCode);
		esimLogDto.setResponseinfo(resultMap.toString());
		esimService.insertEsimLog(esimLogDto);
		return resultMap;
	}
}
