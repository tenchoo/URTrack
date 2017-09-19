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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoEsimGoodsDto;
import com.urt.dto.LaoEsimLogDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.EsimService2;

@Controller
@RequestMapping("/esimChange")
public class EismChangeController {
	private static final Logger log = Logger.getLogger(EismChangeController.class);
    
	
	 private static final String tradeTypecode="转赠";
	
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private EsimService2 esimService;

	@RequestMapping(value = "change", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object> cancelMethod(HttpServletRequest request, HttpServletResponse response) {
		String iccid = request.getParameter("iccid");
		String eid = request.getParameter("eid");
		String lenovoId = request.getParameter("st");
		String goodsId = request.getParameter("goodsId");
		String lenovoIdTarger = request.getParameter("lenovoIdTarger");
		String respCode = "0000";
		Account account = deviceService.authSt(lenovoId);
		//Account accountTarger = deviceService.authSt(lenovoIdTarger);
		LaoEsimGoodsDto dto = esimService.findGoodByGoodsId(goodsId);
		
		
		LaoEsimLogDto esimLogDto = new LaoEsimLogDto();
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isEmpty(lenovoId) || StringUtils.isBlank(eid) || StringUtils.isBlank(iccid)) {
			respCode = "0001";
			log.info("参数不全>>>>>>>>>>>>");
		}else if (null == account) {
			respCode = "0002";
			log.info("LenovoId校验不通过>>>>>>>>>>>>");
		} else if (null == lenovoIdTarger) {
			respCode = "1004";
			log.info("目标号码未注册不能转赠>>>>>>>>>>>>");
		}else if (null == dto) {
			respCode = "0004";
			log.info("产品编码错误>>>>>>>>>>>");
		} else if (account.getUsername().equals(lenovoIdTarger)) {
			respCode = "0009";
			log.info("同一个账号不能做转赠操作>>>>>>>>>>>");
		} else {

		 Map<String,Object> changeMap=esimService.changeServer(account.getUsername(), eid, iccid, lenovoIdTarger, goodsId);
		 if ("9999".equals(changeMap.get("respCode"))) {
				respCode="9999";	 //系统异常
			}else if ("1004".equals(changeMap.get("respCode"))) {
				respCode="1005";    //转赠失败
			}
		}
		resultMap.put("respCode", respCode);

		// 记录日志
		JSONObject requestJson = new JSONObject();
		requestJson.put("eid", eid);
		requestJson.put("lenovoId", lenovoId);
		requestJson.put("goodsId", goodsId);
		requestJson.put("iccid", iccid);
		requestJson.put("lenovoIdTarger", lenovoIdTarger);

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
