package com.urt.web.http.device;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 产品查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/querygoods")
public class QueryGoodsController {
	
	private static  final Logger log=Logger.getLogger(QueryGoodsController.class);
	private static final String SERVERNAME="device+产品查询";
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private ServerCheckService serverService;
	
	@RequestMapping(value = "querygoods", method = { RequestMethod.POST,RequestMethod.GET})
	public void querygoods(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid
			) throws Exception {
		log.info("enter the method querygoods");
		PrintWriter out = response.getWriter();
		JSONObject resultJson=new JSONObject();
		List<Map<String,Object>> info=new ArrayList<Map<String,Object>>();
		String retCode = "1";//-1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
		String custId="";
		iccid = iccid.replace("#", "B");
		try{
			IccidLibDto iccidLibDto = userService.selectByIccid(iccid);
			if(iccidLibDto==null){
				retCode="-7";
			}else{
				custId = iccidLibDto.getCustid();
				String operatorsId = iccidLibDto.getOperators();
				String value1 = iccidLibDto.getValue1();
				String value2 = iccidLibDto.getValue2();
				List<GoodsDto> laoGoodsDtos = goodsOrderService.queryLaoGoods(custId,operatorsId,value1,value2);
				if(laoGoodsDtos==null||laoGoodsDtos.size()<=0){
					retCode="-6";
				}else{
					for(GoodsDto goodsDto:laoGoodsDtos){
						Map<String,Object> goodsMap=new HashMap<String, Object>();
						goodsMap.put("goodId", goodsDto.getGoodsReleaseId()!=null? (goodsDto.getGoodsReleaseId()+""):"");
						goodsMap.put("goodUrlImg", goodsDto.getGoodsPic()!=null?goodsDto.getGoodsPic():"");
						goodsMap.put("goodsName", goodsDto.getGoodsName()!=null?goodsDto.getGoodsName():"");
						goodsMap.put("goodDesc", goodsDto.getGoodsDesc()!=null?goodsDto.getGoodsDesc():"");
						goodsMap.put("goodPrice", goodsDto.getGoodsPrices()!=null?goodsDto.getGoodsPrices():"");
						info.add(goodsMap);
					}
				}
			}
		}catch (Exception e) {
			retCode="-4";
		}
		resultJson.put("retCode", retCode);
		resultJson.put("info", info);
		out.println(resultJson.toString());
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		if(!StringUtil.isBlank(custId)){
			recordDto.setCustId(Long.valueOf(custId));
		}
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method querygoods");
	}
	
	
}
