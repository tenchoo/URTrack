package com.urt.web.http.esim;


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
import com.urt.interfaces.http.device.DeviceProductService;

/**
 * esim 发现服务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/findAD")
public class FindADController {
	
	private static final Logger log=Logger.getLogger(FindADController.class);
	
	@Autowired
	private DeviceProductService deviceProductService;
	
	@RequestMapping(value = "findAD", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JSONObject findserver(HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("enter the method findserver");
		String tag="2";//广告类型
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
		
		log.info("exit the method findserver");
		return resultJson;
	}
	
	
}
