package com.urt.web.esim;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
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

/**
 * esim 订购接口
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/esimOrder")
public class EsimOrderController {

	private static final Logger log = Logger.getLogger(EsimOrderController.class);

	private static final String tradeTypecode = "订购Service";

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private EsimService2 esimService;

	@RequestMapping(value = "order", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> order(HttpServletRequest request, HttpServletResponse response) {
		String lenovoId = request.getParameter("st");
		String eid = request.getParameter("eid");
		String goodsId = request.getParameter("goodsId");
		String orderTag = request.getParameter("orderTag");
		String iccid = request.getParameter("iccid");
		Map<String, Object> resultMap = new HashMap<>();
		LaoEsimLogDto esimLogDto = new LaoEsimLogDto();
		Account account = deviceService.authSt(lenovoId);
		LaoEsimGoodsDto dto = esimService.findGoodByGoodsId(goodsId);
		
		boolean checkIccid=esimService.checkIccid(iccid);
		
		String respCode = "0000";  //0004  产品编号错误  0005 没有空闲的卡资源
		if (StringUtils.isEmpty(lenovoId) || StringUtils.isBlank(eid) || StringUtils.isBlank(goodsId)
				|| StringUtils.isBlank(orderTag)) {
			respCode = "0001";
			log.info("参数不全>>>>>>>>>>>>");
		}else if (null == account) {
			respCode = "0002";
			log.info("LenovoId校验不通过>>>>>>>>>>>>");
		}else if (null == dto) {
			respCode = "0004";
			log.info("产品编码错误>>>>>>>>>>>>>");

		} else if (null != iccid && !"".equals(iccid) && !checkIccid) {
			respCode = "0006";
			log.info("iccid非法>>>>>>>>>>>>>");

		}else {
			resultMap = esimService.orderGoods(account.getUsername(), dto, eid, orderTag, iccid);
			if ("0005".equals(resultMap.get("respCode"))) {
				respCode = "0005"; // 没有空闲的卡资源
			}else if ("1007".equals(resultMap.get("respCode"))){
				respCode = "1007";  //订购失败
			}
		}
		resultMap.put("respCode", respCode);
		// 记录日志
		JSONObject requestJson = new JSONObject();
		requestJson.put("eid", eid);
		requestJson.put("lenovoId", lenovoId);
		requestJson.put("goodsId", goodsId);
		requestJson.put("orderTag", orderTag);
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
