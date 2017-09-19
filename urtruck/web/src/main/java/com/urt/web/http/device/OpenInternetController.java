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
import com.urt.web.common.util.device.SoapConstant;


@Controller
@RequestMapping("/deviceOpenInternet")
public class OpenInternetController {

	
	private static final Logger log = Logger.getLogger(OpenInternetController.class);
	private static final String SERVERNAME="打开公网上网服务";
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
	@RequestMapping(value = "openInter", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> deviceOpenInter(HttpServletRequest request, HttpServletResponse response) {
		Long beginTime = System.currentTimeMillis();
		log.info("打开网络开始：open internet BeginTime: " + beginTime);
		String iccid = request.getParameter("iccid").replace("#", "B");;
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");
        Date date = new Date();
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		try{
			
			
			IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
			Account account = deviceService.authSt(st);
			log.info(">>>>St>>>"+st+"<<<<<<<<<<<<lenovoId>>>>>>>"+account.getUsername());
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
				
				String newcardStatus = "";
				Map<String,String> reqInfo = new HashMap<String,String>();
				reqInfo.put("iccid", iccid);
				CardStatusDto card = cardActiveService.queryCardStatus(reqInfo);
				String cardStatus = card.getCardStatus();
				log.info("*******打开公网上网服务***cardStatus********"+cardStatus);
				newcardStatus = deviceService.cardStatusChange(cardStatus,iccid);
				
				
					//打开公网服务
					String tradeId = tradeService.addTrade(null,userInfo.getCustId().toString(), iccid, "","","140","0");
					//用户归档操作
					String userResult = userService.userArchiving(tradeId);
					log.info("*******打开公网上网服务******userResult*****"+userResult);
					
					
					if("ok".equals(userResult)){
						CardStatusDto card1 = cardActiveService.queryCardStatus(reqInfo);
							//卡状态转换
							String cardStatus1 = card1.getCardStatus();
							log.info("*******打开公网上网服务***cardStatus********"+cardStatus1);
							newcardStatus = deviceService.cardStatusChange(cardStatus1,iccid);
							log.info("*******打开公网上网服务****newcardStatus*******"+newcardStatus);
						resultMap.put("retcode",  "1");
					}else{
						resultMap.put("retcode",  "0");
					}
				
				resultMap.put("nacid",newcardStatus);
				resultMap.put("iccid", iccid);
				
				//调用服务层来加载剩余总流量及当前资费过期时间
				Map<String,String> planResult = deviceService.getCurentPlan(iccid);		
				resultMap.put("expirationDate",planResult.get("expirationDate"));
				resultMap.put("dataRemaining",planResult.get("capacity"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				resultMap.put("now",sdf.format(new Date()));
				resultMap.put("data_limit",SoapConstant.DATA_LIMIT);
				resultMap.put("date_limit",SoapConstant.DATE_LIMIT);
				log.info("打开网络结束：open internet Total cost: " + (System.currentTimeMillis() - beginTime) / 1000.0);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
