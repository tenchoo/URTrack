package com.urt.Ability.unicom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.collect.UserStateChange;
import com.urt.Ability.unicom.service.EditNetworkAccessConfigService;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.service.GetNetworkAccessConfigService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.EditNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.EditTerminalResponse;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.dto.CardStatusDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
/**
 * 联通服务状态变更实现
 * @author sunhao
 *
 */
@Service("userStateChangeCUCC")
public class UserStateChangeCUCC extends UserStateChange {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private EditTerminalService editTerminalService;
	
	@Autowired
	private EditNetworkAccessConfigService editNetworkAccessConfigService;
	
	@Autowired
	private GetNetworkAccessConfigService getNetworkAccessConfigService;
	
	@Autowired
	private UserQueryCardStatus  userQueryCardStatusCUCC;
	
	@Autowired
	private ServiceStatusService  serviceStatusService;
	
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String newStateCode = null;
		Integer serviceId = -1;
		if(args.length > 1)
			newStateCode = (String)args[1];
		if(args.length > 2)
			serviceId = (Integer)args[2];
		String messageId = ZkGenerateSeq.getIdSeq(SeqID.USER_ID);
		ResultMsg msg = new ResultMsg();
		
		Map<String, String>  cardInfo = null;
		try {
			cardInfo = getCountId(iccid);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SOAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XWSSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//查询联通的卡 当前在供应商那边的状态
			String oldStateCode = getOldStatus(iccid, serviceId);
	    	
			log.info(">>>>>>>>>>>>>>>>>>>转换的新的卡状态:"+newStateCode+"当前在供应商那边的旧状态:"+oldStateCode);
	    	//如果老状态是其它状态，需要转换成apn2状态， 需要先将状态修改成ACTIVATED_NAME
			EditTerminalResponse resp = null;
			String jsonStr = null;
	    	if(StringUtils.isNotBlank(oldStateCode) && oldStateCode.equals("TEST_READY_NAME") && StringUtils.isNotBlank(newStateCode) && newStateCode.equals("apn2")){
	    		log.info(">>>>>>>>>>>>>>>>>>>从测试状态到已激活状态");
	    		SOAPMessage sMessage = editTerminalService.call(iccid,messageId,"ACTIVATED_NAME","3"); //可测试状态 到已激活状态
		    	resp = (EditTerminalResponse) editTerminalService.parseMessage(sMessage);
		    	oldStateCode = getOldStatus(iccid, serviceId);
		    	if(StringUtils.isNotBlank(oldStateCode) && oldStateCode.equals("TEST_READY_NAME")){
		    		log.info("................设备状态转换状态出错..................."+oldStateCode);
		    		jsonStr = JSON.toJSONString(resp);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("failed");
					msg.setOpeartorsDealRst("1"); 
		    	}
	    	}else if(StringUtils.isNotBlank(oldStateCode) && !oldStateCode.equals("apn1") && StringUtils.isNotBlank(newStateCode) && !newStateCode.equals("apn2")){
	    		SOAPMessage sMessage = editTerminalService.call(iccid,messageId,newStateCode,"3"); 
		    	resp = (EditTerminalResponse) editTerminalService.parseMessage(sMessage);
		    	String againState = getOldStatus(iccid, serviceId);
		    	if(StringUtils.isNotBlank(againState) && oldStateCode.equals(againState)){
		    		log.info("................设备状态转换状态出错..................."+oldStateCode);
		    		jsonStr = JSON.toJSONString(resp);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("failed");
					msg.setOpeartorsDealRst("1"); 
		    	}else if(StringUtils.isNotBlank(againState) && newStateCode.equals(againState)){
		    		jsonStr = JSON.toJSONString(resp);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("success");
					msg.setSuccess(true);
					msg.setOpeartorsDealRst("0");
		    	}
	    	}
	    	
	    	//如果老状态是apn1 状态 ，需要转换成apn2 状态  即 卡激活操作
			if("100273818".equals(cardInfo.get("accountId")) && !StringUtils.isBlank(oldStateCode) && oldStateCode.contains("apn") && StringUtils.isNotBlank(newStateCode) && newStateCode.contains("apn")){
				Long beginTime = System.currentTimeMillis();
				String targetNacid = AccessConfig.getNacidByapnname(newStateCode);
				SOAPMessage soapMessage = editNetworkAccessConfigService.call(iccid, messageId, targetNacid);
				msg.setInputMessage("iccid="+iccid+" MessageId="+messageId+" targetNacid="+targetNacid);
				log.info("changeAPN cost: " + (System.currentTimeMillis() - beginTime) / 1000.0);
				EditNetworkAccessConfigResponse response = (EditNetworkAccessConfigResponse) editNetworkAccessConfigService.parseMessage(soapMessage);
				if (response != null){
					jsonStr = JSON.toJSONString(response);
					msg.setSuccess(true);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("success");
					msg.setOpeartorsDealRst("0");         //0成功1失败
				}else{
					SOAPFault fault = soapMessage.getSOAPBody().getFault();
					System.err.println("故障信息");
					System.err.println("SOAP Fault Code :" + fault.getFaultCode());
					System.err.println("SOAP Fault String :" + fault.getFaultString());
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					soapMessage.writeTo(bout);
					msg.setOutMessage(bout.toString());
					msg.setOpeartorsDealCode(fault.getFaultCode());
					msg.setOpeartorsDealRst("1"); 		//0成功1失败
				}
			}else if("100273818".equals(cardInfo.get("accountId"))&& !StringUtils.isBlank(oldStateCode) && "ACTIVATION_READY_NAME".equals(oldStateCode) && StringUtils.isNotBlank(newStateCode) && newStateCode.contains("apn")){
				Long beginTime = System.currentTimeMillis();
				String targetNacid = AccessConfig.getNacidByapnname(newStateCode);
				SOAPMessage soapMessage = editNetworkAccessConfigService.call(iccid, messageId, targetNacid);
				msg.setInputMessage("iccid="+iccid+" MessageId="+messageId+" targetNacid="+targetNacid);
				log.info("changeAPN cost: " + (System.currentTimeMillis() - beginTime) / 1000.0);
				EditNetworkAccessConfigResponse response = (EditNetworkAccessConfigResponse) editNetworkAccessConfigService.parseMessage(soapMessage);
				if (response != null){
					jsonStr = JSON.toJSONString(response);
					msg.setSuccess(true);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("success");
					msg.setOpeartorsDealRst("0");         //0成功1失败
				}else{
					SOAPFault fault = soapMessage.getSOAPBody().getFault();
					System.err.println("故障信息");
					System.err.println("SOAP Fault Code :" + fault.getFaultCode());
					System.err.println("SOAP Fault String :" + fault.getFaultString());
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					soapMessage.writeTo(bout);
					msg.setOutMessage(bout.toString());
					msg.setOpeartorsDealCode(fault.getFaultCode());
					msg.setOpeartorsDealRst("1"); 		//0成功1失败
				}
			}else{
				if("ACTIVATED_NAME".equals(oldStateCode) || "DEACTIVATED_NAME".equals(oldStateCode) || "ACTIVATION_READY_NAME".equals(oldStateCode)){
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("success");
					msg.setOpeartorsDealRst("0"); 
				}
				log.info("................设备状态已经激活或状态查询错误..................."+oldStateCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setOpeartorsDealCode("联通状态变更出现异常");
			msg.setOpeartorsDealRst("1"); 		//0成功1失败
		}
		
		return msg;
	}
	
	/**
	 * 查询旧状态方法
	 */
	public String getOldStatus(String iccid, Integer serviceId){
		CardStatusDto cardStatusDto = userQueryCardStatusCUCC.queryCardStatus(iccid);
		String oldStateCode = null;
		if(cardStatusDto != null){
			oldStateCode = cardStatusDto.getCardStatus();
		}else{
			log.info("................联通设备状态查询错误..................."+oldStateCode);
		}
		
		ServiceStatusDto queryState = null;
		if(serviceId != -1 && StringUtils.isNotBlank(oldStateCode)){
			queryState= serviceStatusService.queryState(serviceId, oldStateCode);
		}
		if(queryState != null){
			oldStateCode = queryState.getStatechangeId();
		}else{
			log.info("................联通设备查询将要转换的旧的卡状态 错误..................."+oldStateCode);
		}
		return oldStateCode;
	}
	private Map<String,String> getCountId(String iccid) throws IllegalArgumentException, SOAPException, IOException, XWSSecurityException{
		SOAPMessage request = null;		
		SOAPMessage response = null;
		GetTerminalDetailsResponse responseFirst = null;
		List<String> list = new ArrayList<String>();
		list.add(iccid);
		
		request= getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		SOAPMessage createRequestFirst = getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		request = getTerminalDetailsService.secureMessage(createRequestFirst, getTerminalDetailsService.getUsername(), getTerminalDetailsService.getPasswd());	
		SOAPConnection createConnection = getTerminalDetailsService.getConnectionFactory().createConnection();
		response = createConnection.call(request, new URL(getTerminalDetailsService.getUrl()));
		responseFirst = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
		String accountId = responseFirst.getList().get(0).getAccountId();
		String status = responseFirst.getList().get(0).getStatus();
		//String userFlow = responseFirst.getList().get(0).getMonthToDateUsage();
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("status", status);
		return map;
	}
	/**
	 * 操作之前所做的卡状态检查操作
	 */
	private String getIccidStatus(String userId, String iccid) {
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前查询设备状态...................time:"
				+ beginTime);
		String nowApnName = null;

		GetNetworkAccessConfigResponse response = null;
		AccessConfig accessConfig = null;
		try {
			List<String> list = new ArrayList<String>();
			list.add(iccid);
			SOAPMessage soapMessage = getNetworkAccessConfigService.call(list,userId);
			response = (GetNetworkAccessConfigResponse) getNetworkAccessConfigService
					.parseMessage(soapMessage);
		} catch (IOException | XWSSecurityException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != null) {
			List<AccessConfig> list = response.getList();
			if (list != null && list.size() > 0)
				accessConfig = list.get(0);
		}
		if (accessConfig == null) {
			log.info("获取资费计划异常");
		} else {
			nowApnName = accessConfig.getApnNameByNacid();
		}
		log.info("................激活前查询设备状态.......... Total cost: "
				+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return nowApnName;
	}

}
