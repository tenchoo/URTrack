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

import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.collect.UserOrderProduct;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.unicom.service.EditTerminalRatingService;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.QueueTerminalRatePlanService;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.dto.CardStatusDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.po.IccidLib;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.OperatorPlan;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 联通产品订购实现
 * @author sunhao
 *
 */
@Service("userOrderProductCUCC")
public class UserOrderProductCUCC extends UserOrderProduct<SOAPMessage> {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	@Autowired
	private EditTerminalService editTerminalService;

	@Autowired
	private EditTerminalRatingService editTerminalRatingService;

	@Autowired
	private QueueTerminalRatePlanService queueTerminalRatePlanService;
	
	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO;// 用户套餐
	
	@Autowired
	private LaoUserMapper laoUserMapperDAO;
	
	@Autowired
	private UserQueryCardStatus  userQueryCardStatusCUCC;
	
	@Autowired
	private ServiceStatusService  serviceStatusService;
	
	@Autowired
	IccidLibExtMapper iccidLibExtMapper;

	
	
	
	
	@Override
	protected OrderProductResultMsg sendMessage(Object... args) {
		SOAPMessage request = null;
		SOAPMessage response = null;
		LaoUserOperatorPlan laoUserOperatorPlan = null;
		OrderProductResultMsg msg = new OrderProductResultMsg();
		try {
			OperatorPlan plan = null;
			String iccid = null;
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>args"+args.length +args[0]+"----" +args[1]+"---"+ args[2]+"------"+args[3]);
			if (args == null || args.length == 0 || args.length<3){
				throw new IllegalArgumentException();	
			}
			iccid = (String) args[0];
			plan = (OperatorPlan) args[1];
			laoUserOperatorPlan = (LaoUserOperatorPlan) args[2];
			String messageId = ZkGenerateSeq.getIdSeq(SeqID.USER_ID);
			LaoUser laoUser = (LaoUser) args[3];
			
			IccidLib iccidLib = iccidLibExtMapper.selectByIccid(iccid);
			/*
			//查询联通的卡 当前在供应商那边的状态
			String oldStateCode = getOldStatus(iccid, 1);
			//如果旧状态是其它状态，需要转换成apn2状态， 需要先将状态修改成ACTIVATED_NAME
			EditTerminalResponse resp = null;
			String jsonStr = null;
			if(StringUtils.isNotBlank(oldStateCode) && oldStateCode.equals("TEST_READY_NAME")){
	    		log.info(iccid+"................该卡订购时，为测试状态............................");
	    		SOAPMessage sMessage = editTerminalService.call(iccid,messageId,"ACTIVATED_NAME","3"); //可测试状态 到已激活状态
		    	resp = (EditTerminalResponse) editTerminalService.parseMessage(sMessage);
		    	oldStateCode = getOldStatus(iccid, 1);
		    	if(StringUtils.isNotBlank(oldStateCode) && oldStateCode.equals("TEST_READY_NAME")){
		    		log.info("................设备测试状态转换已激活状态出错..................."+oldStateCode);
		    		jsonStr = JSON.toJSONString(resp);
					msg.setOutMessage(jsonStr);
					msg.setOpeartorsDealCode("卡状态为测试状态，转换激活状态失败");
					msg.setOpeartorsDealRst("1"); 
					return msg;
		    	}
	    	}*/
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
			
			if(laoUserOperatorPlan != null){
				//月付单卡
				if("100979418".equals(cardInfo.get("accountId"))){
					//月付单卡中的月付套餐
					if("0".equals(plan.getPlanType())){ //等于0 说明没有资费计划了
						request = editTerminalService.createRequest(iccid, messageId, plan.getOperatorsPid());
						request = editTerminalService.secureMessage(request, editTerminalService.getUsername(), editTerminalService.getPasswd());
						ByteArrayOutputStream bin=new ByteArrayOutputStream();
						request.writeTo(bin);
						msg.setInputMessage(bin.toString());
						System.out.println("Request: ");
						request.writeTo(System.out);
						System.out.println("");
						SOAPConnection connection = editTerminalService.getConnectionFactory().createConnection();
						response = connection.call(request, new URL(editTerminalService.getUrl()));
						System.out.println("Response: ");
						response.writeTo(System.out);
						System.out.println("");
					}
					//月付单卡中的预付套餐
					if("1".equals(plan.getPlanType())){
						System.out.println(RatePlanNum(iccid));
						String info[] = RatePlanNum(iccid).split("=");
						int ratePlan = Integer.parseInt(info[0]);
						if(0==ratePlan||(1==ratePlan && info[1].indexOf("MON-IND") != -1)){ //等于0 说明没有资费计划了
							request = editTerminalService.createRequest(iccid, messageId, plan.getOperatorsPid());
							request = editTerminalService.secureMessage(request, editTerminalService.getUsername(), editTerminalService.getPasswd());
							ByteArrayOutputStream bin=new ByteArrayOutputStream();
							request.writeTo(bin);
							msg.setInputMessage(bin.toString());
							System.out.println("Request: ");
							request.writeTo(System.out);
							System.out.println("");
							SOAPConnection connection = editTerminalService.getConnectionFactory().createConnection();
							response = connection.call(request, new URL(editTerminalService.getUrl()));
							System.out.println("Response: ");
							response.writeTo(System.out);
							System.out.println("");
						}else{
							request = queueTerminalRatePlanService.createRequest(iccid, messageId, plan.getOperatorsPid());
							request = queueTerminalRatePlanService.secureMessage(request, queueTerminalRatePlanService.getUsername(), queueTerminalRatePlanService.getPasswd());
							ByteArrayOutputStream bin=new ByteArrayOutputStream();
							request.writeTo(bin);
							msg.setInputMessage(bin.toString());
							System.out.println("Request: ");
							request.writeTo(System.out);
							System.out.println("");
							SOAPConnection connection = queueTerminalRatePlanService.getConnectionFactory().createConnection();
							response = connection.call(request, new URL(queueTerminalRatePlanService.getUrl()));
							System.out.println("Response: ");
							response.writeTo(System.out);
							System.out.println("");
						}
					}
				//月付灵活共享
				}else if("100979618".equals(cardInfo.get("accountId"))){
					/*request = editTerminalService.createRequest(iccid, messageId, plan.getOperatorsPid());
					request = editTerminalService.secureMessage(request, editTerminalService.getUsername(), editTerminalService.getPasswd());
					ByteArrayOutputStream bin=new ByteArrayOutputStream();
					request.writeTo(bin);
					msg.setInputMessage(bin.toString());
					System.out.println("Request: ");
					request.writeTo(System.out);
					System.out.println("");
					SOAPConnection connection = editTerminalService.getConnectionFactory().createConnection();
					response = connection.call(request, new URL(editTerminalService.getUrl()));
					System.out.println("Response: ");
					response.writeTo(System.out);
					System.out.println("");*/
				}else{
					if(laoUserOperatorPlan.getGoodsIndex() == 1){ //等于0 说明没有资费计划了
						request = editTerminalService.createRequest(iccid, messageId, plan.getOperatorsPid());
						request = editTerminalService.secureMessage(request, editTerminalService.getUsername(), editTerminalService.getPasswd());
						ByteArrayOutputStream bin=new ByteArrayOutputStream();
						request.writeTo(bin);
						msg.setInputMessage(bin.toString());
						System.out.println("Request: ");
						request.writeTo(System.out);
						System.out.println("");
						SOAPConnection connection = editTerminalService.getConnectionFactory().createConnection();
						response = connection.call(request, new URL(editTerminalService.getUrl()));
						System.out.println("Response: ");
						response.writeTo(System.out);
						System.out.println("");
					}else if((laoUserOperatorPlan.getGoodsIndex() ==2 && null != iccidLib && !iccidLib.getCardstatus().equals("4")) 
							||(laoUserOperatorPlan.getGoodsIndex() ==2 && null != iccidLib && "0".equals(iccidLib.getActived()) && 
							!"ACTIVATION_READY_NAME".equals(cardInfo.get("status")))){
						request = editTerminalRatingService.createRequest(iccid, messageId, plan.getOperatorsPid());
						request = editTerminalRatingService.secureMessage(request, editTerminalRatingService.getUsername(), editTerminalRatingService.getPasswd());
						ByteArrayOutputStream bin=new ByteArrayOutputStream();
						request.writeTo(bin);
						msg.setInputMessage(bin.toString());
						System.out.println("Request: ");
						request.writeTo(System.out);
						System.out.println("");
						SOAPConnection connection = editTerminalRatingService.getConnectionFactory().createConnection();
						response = connection.call(request, new URL(editTerminalRatingService.getUrl()));
						System.out.println("Response: ");
						response.writeTo(System.out);
						//response.writeTo(out);
						System.out.println("");
					}else{
						request = queueTerminalRatePlanService.createRequest(iccid, messageId, plan.getOperatorsPid());
						request = queueTerminalRatePlanService.secureMessage(request, queueTerminalRatePlanService.getUsername(), queueTerminalRatePlanService.getPasswd());
						ByteArrayOutputStream bin=new ByteArrayOutputStream();
						request.writeTo(bin);
						msg.setInputMessage(bin.toString());
						System.out.println("Request: ");
						request.writeTo(System.out);
						System.out.println("");
						SOAPConnection connection = queueTerminalRatePlanService.getConnectionFactory().createConnection();
						response = connection.call(request, new URL(queueTerminalRatePlanService.getUrl()));
						System.out.println("Response: ");
						response.writeTo(System.out);
						System.out.println("");
					}
				}
				ByteArrayOutputStream bout=new ByteArrayOutputStream();
				response.writeTo(bout);
				if (!response.getSOAPBody().hasFault()) {
					msg.setSuccess(true);
					msg.setOutMessage(bout.toString());
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0");          //0成功1失败
				} else {
					SOAPFault fault = response.getSOAPBody().getFault();
					System.err.println("故障信息");
					System.err.println("SOAP Fault Code :" + fault.getFaultCode());
					System.err.println("SOAP Fault String :" + fault.getFaultString());
					msg.setOutMessage(bout.toString());
					msg.setOpeartorsDealCode(fault.getFaultCode());
					msg.setOpeartorsDealRst("1"); 			//0成功1失败
				}
			}
			
			Thread.sleep(100);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>>>>>>联通订购异常>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			e.printStackTrace();
			msg.setOpeartorsDealCode("联通订购出现异常");
			msg.setOpeartorsDealRst("1"); 			//0成功1失败
			msg.setSuccess(false);
		}
	    return msg;
	}
	
	public static void main(String[] args) {
		String str ="1=110WLW028203_MON-IND_10M_SP";
		//System.out.println(str.split("=")[0]);
		//System.out.println(str.split("=")[1]);
		System.out.println(str.split("=")[1].indexOf("MON") !=-1);
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
}
