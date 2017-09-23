package com.urt.web.http.device;

import java.text.SimpleDateFormat;
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
import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.device.Sha256;


@Controller
@RequestMapping("/deviceQueryOnline")
public class QueryOnlineFlowController {
	
	private static final String SERVERNAME="查询数据上网服务流量";
	private static final Logger log = Logger.getLogger(QueryGoodsController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	TradeService tradeService;
	@Autowired
	private CardActiveService cardActiveService;
	
	@Autowired
	private ServerCheckService serverService;
	@RequestMapping(value = "queryOnline", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> queryOnline(HttpServletRequest request, HttpServletResponse response) {
		Long beginTime = System.currentTimeMillis();
		log.info("打开网络开始：open internet BeginTime: " + beginTime);
		String iccid = request.getParameter("iccid").replace("#", "B");;
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");
		
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		SimpleDateFormat sdf=null;	
		Date date=new Date();
		try{
		
			IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
			Account account = deviceService.authSt(st);
			LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
			if (StringUtils.isEmpty(iccid) || StringUtils.isBlank(st) || StringUtils.isBlank(deviceid)
					|| StringUtils.isBlank(s)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
			}else if(!s.equalsIgnoreCase(Sha256.sha256(Sha256.sha256(iccid + deviceid + st)))){
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + iccid);
			}else if(null == iccidLibrary){
				resultMap.put("retcode", "-7");// 系统异常,iccid库不存在此卡
				log.info("非法iccid");
			}else if(account == null){
				resultMap.put("retcode", "-2");// st校验失败
				log.info("*****");
			}else if(userInfo == null){
				resultMap.put("retcode", "-4");//系统异常
				log.info("未关联Lenovoid iccid="+iccid);		
			}else{
				Map<String,String> planResult = deviceService.getCurentPlan(iccid);
				String newcardStatus = "";
				Map<String,String> reqInfo = new HashMap<String,String>();
				reqInfo.put("iccid", iccid);
				CardStatusDto card = cardActiveService.queryCardStatus(reqInfo);
				if(card == null || "".equals(card.getCardStatus())){
					throw new Exception("查询卡状态报错");
				}else{
						//卡状态转换
					String cardStatus = card.getCardStatus();
					log.info("***查询数据上网服务流量**cardStatus*******"+cardStatus);	
					newcardStatus = deviceService.cardStatusChange(cardStatus,iccid);
					log.info("***查询数据上网服务流量**newcardStatus*******"+newcardStatus);
				}
				resultMap.put("nacid", newcardStatus);
				resultMap.put("ratePlanName", planResult.get("ratePlanName"));
				resultMap.put("expirationDate", planResult.get("expirationDate"));
				resultMap.put("termLength", planResult.get("termLength"));
				resultMap.put("dataRemaining", planResult.get("dataRemaining"));
				resultMap.put("capacity", planResult.get("capacity"));
				resultMap.put("iccid", iccid);
				sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultMap.put("timestamp", sdf.format(new Date()));
				resultMap.put("retcode", "1");
				recordDto.setCustId(userInfo.getCustId());
			}

		}catch (Exception e){
			resultMap.put("retcode", "-4");//系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		if("1".equals(resultMap.get("retcode"))){
			recordDto.setIsSuccess("0");
		}else{
			recordDto.setIsSuccess("1");
		}	
		sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordDto.setErrorCode(resultMap.get("retcode").toString());	
		recordDto.setRspInfo(new JSONObject().toJSONString(resultMap));
		recordDto.setIpAddress(request.getRemoteHost());
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);	
		recordDto.setAccessDate(date);
		recordDto.setParaName1("device");
		recordDto.setParaName2(sdf.format(new Date()));
		recordDto.setReqInfo(reqJson.toString());
		serverService.savaLogerToDb(recordDto);	
		return resultMap;
	}

}
