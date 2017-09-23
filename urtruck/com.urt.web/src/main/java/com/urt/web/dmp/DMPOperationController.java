package com.urt.web.dmp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.dmp.LaoDMPLogDto;
import com.urt.dto.http.StopOnDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.dmp.DMPLogService;
import com.urt.interfaces.dmp.DMPOperationService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.StringUtil;
import com.urt.web.util.RandomUtil;

@Controller
@RequestMapping("/operate")
public class DMPOperationController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private DMPOperationService dmpOperationService;
	@Autowired
	private DMPLogService DMPLogServiceImpl;
	@Autowired
	private CardActiveService cardActiveService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/stopWifi")
	@ResponseBody
	public boolean stopWifi(HttpServletRequest req, String imei,String custid){
		Boolean isStop = dmpOperationService.stopWifi(imei);
		
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String operator = user.getLoginName();
		
		LaoDMPLogDto logDto=new LaoDMPLogDto();
		logDto.setCustId(Long.parseLong(custid));
		logDto.setImei(imei);
		logDto.setIsAgainst("0");//0没有违规，1违规
		logDto.setOperateType("手工操作");
		logDto.setOperationComment("停WiFi");
		logDto.setTriggerCause("手工操作");
		logDto.setOperateUser(operator);
		if(isStop)
			logDto.setIsSuccess("1");//0失败，1成功
		else
			logDto.setIsSuccess("0");
		String logid = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
		logDto.setId(Long.parseLong(logid));
	//	long logid = RandomUtil.generateLong(16);
	//	logDto.setId(logid);
		DMPLogServiceImpl.insertDMPLog(logDto);
		return isStop;
	}
	
	@RequestMapping("/openWifi")
	@ResponseBody
	public boolean openWifi(HttpServletRequest req, String imei,String custid){
		Boolean isOpen = dmpOperationService.openWifi(imei);
		
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String operator = user.getLoginName();
		
		LaoDMPLogDto logDto=new LaoDMPLogDto();
		logDto.setCustId(Long.parseLong(custid));
		logDto.setImei(imei);
		logDto.setIsAgainst("0");//0没有违规，1违规
		logDto.setOperateType("手工操作");
		logDto.setOperationComment("开启WiFi");
		logDto.setTriggerCause("手工操作");
		logDto.setOperateUser(operator);
		if(isOpen)
			logDto.setIsSuccess("1");//0失败，1成功
		else
			logDto.setIsSuccess("0");
		String logid = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
		logDto.setId(Long.parseLong(logid));
		//long logid = RandomUtil.generateLong(16);
		//logDto.setId(logid);
		DMPLogServiceImpl.insertDMPLog(logDto);
		
		return isOpen;
	}
	
	@RequestMapping("/stopOnCard")
	@ResponseBody
	public boolean stopOn(HttpServletRequest req, String imei,String custid,String iccid,String commend){//0停卡操作，1开卡操作
		log.info("进入方法stopOn");
		log.info("imei="+imei);
		log.info("custid="+custid);
		log.info("iccid="+iccid);
		log.info("commend="+commend);
		boolean result=false;
		StringBuffer exceptionStr=new StringBuffer();
		if("0".equals(commend)){
			exceptionStr.append("停卡");
		}else if("1".equals(commend)){
			exceptionStr.append("开卡");
		}
		try{
			if(StringUtil.isBlank(iccid)){
				log.info("iccid为空");
				exceptionStr.append("、iccid为空");
			}else{
				IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
				if(iccidLibrary==null){
					exceptionStr.append("、此卡未注册:"+iccid);
					log.info("此卡未注册:"+iccid);
				}
				
				if(custid==null||"".equals(custid)){
					exceptionStr.append("、卡与用户未绑定");
					log.info("卡与用户未绑定");
				}
				
				Map<String, String> reqInfo = new HashMap<String, String>();
				reqInfo.put("iccid", iccid);
				reqInfo.put("custId", custid);
				reqInfo.put("ifMaintenance", "0");
				reqInfo.put("tradeTypeCode", "110");
				CardStatusDto queryCardStatus = cardActiveService.queryCardStatus(reqInfo);
				String cardStatus = queryCardStatus.getCardStatus();//1在用状态  2停机状态
				log.info("cardStatus="+cardStatus);
				if("1".equals(cardStatus)){
					if("1".equals(commend)){
						exceptionStr.append("、用户重复开卡,卡已经是开卡状态");
						log.info("用户重复开卡,卡已经是开卡状态");
						result=true;
					}else{
						log.info("进行停卡操作");
						result=stopOnUtil(commend,iccid,custid);
						log.info("result="+result);
					}
				}else if("2".equals(cardStatus)){
					if("0".equals(commend)){
						exceptionStr.append("、用户重复停卡,卡已经是停卡状态");
						log.info("用户重复停卡,卡已经是停卡状态");
						result=true;
					}else{
						log.info("进行开卡操作");
						result=stopOnUtil(commend,iccid,custid);
						log.info("result="+result);
					}
				}
			}
		}catch(Exception e){
			exceptionStr.append(",系统异常");
			e.printStackTrace();
		}	

		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String operator = user.getLoginName();
		
		LaoDMPLogDto logDto=new LaoDMPLogDto();
		logDto.setCustId(Long.parseLong(custid));
		logDto.setImei(imei);
		logDto.setIsAgainst("0");//0没有违规，1违规
		logDto.setOperateType("手工操作");
		logDto.setOperationComment(exceptionStr.toString());
		logDto.setTriggerCause("手工操作");
		logDto.setOperateUser(operator);
		if(result)
			logDto.setIsSuccess("1");//0失败，1成功
		else
			logDto.setIsSuccess("0");
		String logid = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
		logDto.setId(Long.parseLong(logid));
	//	long logid = RandomUtil.generateLong(16);
		//logDto.setId(logid);
		DMPLogServiceImpl.insertDMPLog(logDto);
		log.info("走出方法stopOn");
		return result;
	}
	private boolean stopOnUtil(String commend,String iccid,String custid){
		log.info("进入接口stopOnUtil");
		log.info("校验通过，进行停开卡操作");
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("stateCode", commend);
		hashMap.put("iccid", iccid);
		hashMap.put("custId", custid);
		log.info("hashMap="+hashMap);
		StopOnDto stopOn = cardActiveService.stopOn(hashMap);
		log.info("stopOn.getIsSuccess()="+stopOn.getIsSuccess());
		log.info("stopOn.getRespCode()"+stopOn.getRespCode());
		log.info("stopOn.getRespdesc()"+stopOn.getRespDesc());
		log.info("走出stopOnUtil");
		if("0".equals(stopOn.getIsSuccess()))
			return true;
		
		return false;
	}
}
