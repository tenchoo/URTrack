package com.urt.web.http.device;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.pay.utils.JsonUtil;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.device.RedisService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.device.Sha256;
import com.urt.web.common.util.device.SoapConstant;

@Controller
@RequestMapping("/deviceHeartCheck")
public class HeartCheckController {
	
	
	private static final String SERVERNAME="心跳";
	private static final Logger log = Logger.getLogger(HeartCheckController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	TradeService tradeService;
	
	@Autowired
	private RedisService redisClientService;
	@Autowired
	private ServerCheckService serverService;
	String decode = "";
	/**充值链接url**/
	@Value("${alive.chargeActionUrl}")
	private String chargeActionUrl;
	public static String REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD = "REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD";
	public static String REDIS_ALIVE_CHECK_CATAGORY_PARENT = "REDIS_ALIVE_CHECK_CATAGORY_PARENT";
	@Value("${alive.period}")
	private String alivePeriod;
	private String iccid;
	private String lanversion;
	
	@RequestMapping(value = "heartCheck", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> heartCheck(HttpServletRequest request, HttpServletResponse response){
		Long beginTime = System.currentTimeMillis();
		log.info("打开网络开始：open internet BeginTime: " + beginTime);
		iccid = request.getParameter("iccid").replace("#", "B");;
		String deviceid = request.getParameter("deviceid");
		String devicemodel = request.getParameter("devicemodel");
		String rom = request.getParameter("rom");
		String deviceversion = request.getParameter("deviceversion");
		lanversion = request.getParameter("lanversion");
		
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("devicemodel", devicemodel);
		reqJson.put("deviceid", deviceid);
		reqJson.put("rom", rom);
		reqJson.put("deviceversion", deviceversion);
		reqJson.put("lanversion", lanversion);
		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		
		try {
			if(devicemodel != null && !devicemodel.equals(""))
				 decode = URLDecoder.decode(devicemodel, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
			LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
			if (StringUtils.isEmpty(iccid)  || StringUtils.isBlank(deviceid)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
			}else if(null == iccidLibrary){
				resultMap.put("retcode", "-7");// 系统异常,iccid库不存在此卡
				log.info("非法iccid");
			}else if(userInfo == null){
				resultMap.put("retcode", "-4");//系统异常
				log.info("未关联Lenovoid iccid="+iccid);		
			}else{
				Long now = System.currentTimeMillis();
				int seconds = Integer.parseInt("30") * 60;//心跳维持时间5分钟
				try{
					Long result = redisClientService.hset(REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD, iccid, now + "", seconds);
					if(result != null) {
						redisClientService.hset(REDIS_ALIVE_CHECK_CATAGORY_PARENT, iccid, now + "");
					}
				}catch(Exception e) {
					e.printStackTrace();
					resultMap.put("retcode", "-4");
					log.info("从redis中put数据失败---");
					return resultMap;
				}
				/*String cardDetails = deviceService.getActivityCardDetail(rom, deviceid, devicemodel, deviceversion);
				if(cardDetails == null) {
					resultMap.put("retcode", "-4");
					log.info("获取getActivityCardDetail失败---");
					return resultMap;
				}*/
				String data="";
				if(StringUtils.isEmpty(data)) {
					resultMap.put("version", "0");
				}else {
					resultMap.put("version", "1");
				}
				resultMap.put("retcode", "1");
				addWarning(resultMap);
				addIccidReleationStatus(resultMap);
				recordDto.setCustId(userInfo.getCustId());
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
	
	private void addWarning(Map<String, Object> outMaps) {
		String dataLimitKey = iccid + "-" + SoapConstant.NOTITY_TYPE_FLOW;
		String expireLimitKey = iccid + "-" + SoapConstant.NOTITY_TYPE_DATE;
		String rateNotifyKey = iccid + "-" + SoapConstant.RATE_NOTIFY;//
		log.info("rateNotifyKey>>>>>>>>>>>>>"+rateNotifyKey);
		log.info("expireLimitKey>>>>>>>>>>>>"+expireLimitKey);
		log.info("dataLimitKey>>>>>>>>>>>>>"+dataLimitKey);
		String dataLimit = redisClientService.get(dataLimitKey);
		String expireLimit = redisClientService.get(expireLimitKey);
		String rateNotify = redisClientService.get(rateNotifyKey);//
		log.info("Redis中获取的值dataLimit>>>>>>>>>>>>>"+dataLimit);
		log.info("Redis中获取的值expireLimit>>>>>>>>>>>>"+expireLimit);
		log.info("Redis中获取的值rateNotify>>>>>>>>>>>>>"+rateNotify);
	
		Map<String, String> map = new HashMap<String, String>();
		String title = null;
		String content = null;
		String action = null;
		String actionName = null;
		
		//判断是否实名认证
		
		
	/*	if(decode != null && decode.equalsIgnoreCase("Lenovo YB1-X90L") || decode.equalsIgnoreCase("Lenovo TB-X304N") ){
			log.info("devicemodel-----------------:"+decode);*/
			if(StringUtils.isNotEmpty(rateNotify)) {

				log.info("-------rateNotify-------"+rateNotify);
				if(StringUtils.isNotBlank(lanversion) && "en".equals(lanversion)){
		    			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    			Date date=new Date();
						title = SoapConstant.EVENT_TITLE_NOTIFY_EN;
						content = MessageFormat.format(SoapConstant.EVENT_CONTENT_NOTIFY_EN,dateFormater.format(date),rateNotify);
				}else{
	    			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    			Date date=new Date();
					title = SoapConstant.EVENT_TITLE_NOTIFY_CN;
					content = MessageFormat.format(SoapConstant.EVENT_CONTENT_NOTIFY_CN,dateFormater.format(date),rateNotify);
				}
				
				action = "notification";
				redisClientService.del(rateNotifyKey);//拿到数据后清除缓存
				log.info("=====键是否存在===="+redisClientService.exists(rateNotifyKey));
			}
		/*}else{
			redisClientService.del(rateNotifyKey);
			log.info("----删除信息---rateNotifyKey"+rateNotifyKey);
			Boolean exists = redisClientService.exists(rateNotifyKey);			
			log.info("=====非Yoga设备键是否存在==="+exists);
		}
		*/
		if(StringUtils.isNotEmpty(dataLimit)) {
//			outMaps.put("dataRemainWarning", dataLimit);
//			redisClientService.del(dataLimitKey);
			if(StringUtils.isNotBlank(lanversion) && "en".equals(lanversion)){
					title = SoapConstant.EVENT_TITLE_FLOW_EN;
					content = MessageFormat.format(SoapConstant.EVENT_CONTENT_FLOW_EN, SoapConstant.DATA_LIMIT);
			}else{
				title = SoapConstant.EVENT_TITLE_FLOW_CN;
				content = MessageFormat.format(SoapConstant.EVENT_CONTENT_FLOW_CN, SoapConstant.DATA_LIMIT);
			}
			
			action = chargeActionUrl;
			actionName = "去充值";
			redisClientService.del(dataLimitKey);//拿到数据后清除缓存
			log.info("=====键是否存在===="+redisClientService.exists(dataLimitKey));
		}
		
		if(StringUtils.isNotEmpty(expireLimit)) {
//			outMaps.put("expireWarning", expireLimit);
//			redisClientService.del(expireLimit);
			
			if(StringUtils.isNotBlank(lanversion) && "en".equals(lanversion)){
					title = SoapConstant.EVENT_TITLE_DATE_EN;
					content = MessageFormat.format(SoapConstant.EVENT_CONTENT_DATE_EN, SoapConstant.DATE_LIMIT);
			}else{
				title = SoapConstant.EVENT_TITLE_DATE_CN;
				content = MessageFormat.format(SoapConstant.EVENT_CONTENT_DATE_CN, SoapConstant.DATE_LIMIT);
			}
			
			action =chargeActionUrl;
			actionName = "去充值";
			redisClientService.del(expireLimitKey);//拿到数据后清除缓存
			log.info("=====键是否存在===="+redisClientService.exists(expireLimitKey));
		}
			
		if (StringUtils.isNotBlank(title)) {
			map.put("title", title);
		}
		if (StringUtils.isNotBlank(content)) {
			map.put("content", content);
		}
		if (StringUtils.isNotBlank(action)) {
			map.put("action", action);
		}
		if (StringUtils.isNotBlank(actionName)) {
			map.put("actionName", actionName);
		}
		
		
		
		outMaps.put("warning", map);
	}
	
	private void addIccidReleationStatus(Map<String, Object> outMaps) {
		//TODO 校验绑定关系
		outMaps.put("bindStatus", "1");
	}
}
