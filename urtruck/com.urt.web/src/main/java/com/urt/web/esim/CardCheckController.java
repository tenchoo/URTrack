package com.urt.web.esim;

import java.util.Date;
import java.util.HashMap;
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
import com.urt.dto.LaoEsimLogDto;
import com.urt.interfaces.esim.EsimService2;

@Controller
@RequestMapping("/esimCheck")
public class CardCheckController {
        
 private static final  Logger log=Logger.getLogger(CardCheckController.class);
 
 @Autowired
 private  EsimService2 esimService;

    private static final String tradeTypecode="eid检查";
	@RequestMapping(value="cardCheck",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody Map<String, Object> cardCheck(HttpServletRequest reqest,HttpServletResponse response){
		
		String eid = reqest.getParameter("eid");
		String imei = reqest.getParameter("imei");
		Map<String, Object>   respMap=new HashMap<>();
		LaoEsimLogDto esimLogDto = new LaoEsimLogDto();
		String respCode="0000";  // 0000  成功  0001缺少参数 0002 lenovoId失效
		if (null==eid || null==imei || "".equals(eid) || "".equals(imei)) {
			respCode="0001";
			//respMap.put("respCode", "0001"); //参数不能为空
			log.info("esim校验接口日志 >>>>>>>>>>>参数不全");
		}else{
			if (!esimService.checkCard(imei,eid)) {
				respCode="1002";
				//respMap.put("respCode", "0002"); //eid和设备不匹配
				log.info("esim校验接口日志 >>>>>>>>>>>eid和设备不匹配");
			}
		}
		respMap.put("respCode",respCode); //校验成功
		//记录日志 
		JSONObject requestJson = new JSONObject();
		requestJson.put("eid", eid);
		requestJson.put("imei", imei);
		esimLogDto.setEid(eid);
		esimLogDto.setRequestinfo(requestJson.toJSONString());
		esimLogDto.setIndate(new Date());
		esimLogDto.setTradeTypecode(tradeTypecode);
		esimLogDto.setRespcode(respCode);
		esimLogDto.setResponseinfo(respMap.toString());
		
		esimService.insertEsimLog(esimLogDto);
		
		return respMap;
	}
	
	
}
