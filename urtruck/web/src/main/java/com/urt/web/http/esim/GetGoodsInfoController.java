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
import com.urt.interfaces.esim.ESIMService;
/**
 * device+ 获取流量包信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/esimQueryGoods")
public class GetGoodsInfoController {
	
	private static final Logger log=Logger.getLogger(GetGoodsInfoController.class);
	@Autowired
	private ESIMService esimService;
	
	
	@RequestMapping(value = "queryGoods", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JSONObject  getGoodsInfo(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		log.info("enter the method info");
		JSONObject jsonObject=new JSONObject();
		List<Map<String, String>> goodInfo = null;
		String retcode="1";
		try {
			goodInfo  = esimService.queryGoods();
		} catch (Exception e) {
			e.printStackTrace();
			retcode="-4";
		}
		jsonObject.put("retcode", retcode);
		jsonObject.put("goodInfo", goodInfo);
		log.info("exit the method info");
		return jsonObject;
	}
	
	
}
