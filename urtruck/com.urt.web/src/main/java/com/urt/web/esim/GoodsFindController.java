package com.urt.web.esim;

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
import com.urt.dto.LaoEsimLogDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.esim.EsimService2;
/**
 * 产品列表查询
 * @author admin
 *
 */
@Controller
@RequestMapping("/esimGoods")
public class GoodsFindController {
    
	private static final Logger log=Logger.getLogger(GoodsFindController.class);
	
	@Autowired
	private EsimService2 esimService;
	
	@Autowired
	private DeviceService deviceService;
	
	
	
	@RequestMapping(value = "find", method = { RequestMethod.POST,RequestMethod.GET})
	
	public  @ResponseBody JSONObject queryGoods(HttpServletRequest request,HttpServletResponse response){
		String lenovoId = request.getParameter("st");
		JSONObject respJson = new JSONObject();
		String  respCode="0000";
		if (null==lenovoId || "".equals(lenovoId)) {
			respCode="0001";
			respJson.put("respCode", respCode);
			log.info("参数不全>>>>>>>>>>>>>>");
			return respJson;
		}
		Account Account = deviceService.authSt(lenovoId);
		if (null==Account) {
			respCode="0002";
			log.info("lenovoId 校验不通过");
		}else{
			List<Map<String, Object>> queryGoods = esimService.queryGoods();
			
			respJson.put("productInfo", queryGoods);
			
			
		}
		respJson.put("respCode", respCode);
		
		return respJson;
	}
}
