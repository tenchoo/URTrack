package com.urt.web.esim;

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
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.EsimService2;
import com.urt.interfaces.esim.EsimUserService;

/**
 * 订购产品查询
 * @author admin
 *
 */
@Controller
@RequestMapping("/esimorderGoods")
public class OrderGoodsController {
    
	private static final  Logger log=Logger.getLogger(CardCheckController.class);
	
	@Autowired
	private  DeviceService  deviceService;
	
	@Autowired
	private  EsimService2    esimService;
	
	@RequestMapping(value="/ordergoods",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody JSONObject orderGoodsFind(HttpServletRequest reqest,HttpServletResponse response){
		
		String lenovoId = reqest.getParameter("st");
		JSONObject respJSON = new JSONObject();
		Account account = deviceService.authSt(lenovoId);
		if (null==account) {
			respJSON.put("respCode", "0002");
			log.info("LenovoId校验不通过>>>>>>>>>>>>");
			return respJSON;
		}
		List<Map<String,Object>> list= esimService.findOrderGoods(account.getUsername());
		
		respJSON.put("respCode", "0000");
		respJSON.put("orderProductInfo",list);
	
		return respJSON;
	}

	
}
