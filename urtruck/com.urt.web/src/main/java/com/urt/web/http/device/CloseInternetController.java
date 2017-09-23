package com.urt.web.http.device;

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
@RequestMapping("/deviceCloseInternet")
public class CloseInternetController {
	
	private static final String SERVERNAME="关闭公网上网服务";
	private static final Logger log = Logger.getLogger(QueryGoodsController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private CardActiveService cardActiveService;
	@Autowired
	TradeService tradeService;
	@Autowired
	private ServerCheckService serverService;
	@RequestMapping(value = "closeInter", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,String> closeInternet(HttpServletRequest request, HttpServletResponse response) {
		Long beginTime = System.currentTimeMillis();
		log.info("关闭网络开始：close internet BeginTime: " + beginTime);
		String iccid = request.getParameter("iccid").replace("#", "B");
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");


		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		Map<String, String> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		
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
				//关闭公网服务
				String tradeId = tradeService.addTrade(null,userInfo.getCustId().toString(), iccid, "","","130","0");
				//用户归档操作
				String userResult = userService.userArchiving(tradeId);
				log.info("****关闭公网上网服务***userResult****"+userResult);
				String newcardStatus = "";
				Map<String,String> reqInfo = new HashMap<String,String>();
				reqInfo.put("iccid", iccid);
				if("ok".equals(userResult)){
					CardStatusDto card = cardActiveService.queryCardStatus(reqInfo);
					
					if(card == null || "".equals(card.getCardStatus())){
						throw new Exception("查询卡状态报错");
					}else{
						//卡状态转换
						String cardStatus = card.getCardStatus();
						log.info("****关闭公网上网服务***cardStatus****"+cardStatus);
						newcardStatus = deviceService.cardStatusChange(cardStatus,iccid);
						log.info("****关闭公网上网服务***newcardStatus****"+newcardStatus);
					}
					resultMap.put("retcode",  "1");
				}else{
					resultMap.put("retcode",  "0");
				}
				resultMap.put("nacid", newcardStatus);	
				resultMap.put("iccid", iccid);
				log.info("关闭网络结束: close internet Total cost: " + (System.currentTimeMillis() - beginTime) / 1000.0);
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
		recordDto.setErrorCode(resultMap.get("retcode").toString());	
		recordDto.setRspInfo(new JSONObject().toJSONString(resultMap));
		recordDto.setIpAddress(request.getRemoteHost());
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);	
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		recordDto.setReqInfo(reqJson.toString());
		serverService.savaLogerToDb(recordDto);	
		return resultMap;
	}
}
