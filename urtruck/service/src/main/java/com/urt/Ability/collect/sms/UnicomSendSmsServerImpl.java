package com.urt.Ability.collect.sms;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.Ability.unicom.service.GetSMSDetailsService;
import com.urt.Ability.unicom.service.SendSMSService;
import com.urt.Ability.unicom.vo.GetSMSDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalsByMsisdnResponse;
import com.urt.Ability.unicom.vo.SmsMessage;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoSmsInfoMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoSmsInfo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

import net.sf.json.JSONObject;

@Service("unicomSendSmsServerImpl")
public class UnicomSendSmsServerImpl  extends SendSmsAbstract{
	protected static final Logger logger = Logger.getLogger(UnicomSendSmsServerImpl.class);
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	@Autowired
	private LaoSmsInfoMapper laoSms ;
	@Autowired
	private SendSMSService SendSMSService;
	@Autowired
	private GetSMSDetailsService getSMSDetailsService;
	
	public static void main(String[] args) {
		/*UnicomSendSmsServerImpl u = new UnicomSendSmsServerImpl();
		StringBuffer str = new StringBuffer();
		str.append("1001").append("\t")
		.append("89860616020020763137").append("\t").append("hello").append("\t").append("23231312121212");
		u.sendSMS(str.toString());*/
	}
	
	@Override
	public String sendSMS(Map<String,Object> smsContent) {
		String messageId = ZkGenerateSeq.get8IdSeq(SeqID.USER_ID);
		String iccid = smsContent.get("number").toString();
		String messageText = smsContent.get("content").toString();
		String respInfo = getSMSResult(messageId,iccid,messageText,"123");
		
		Map<String,Object> o = new HashMap<String,Object>();
		o.put("smsMsgid", respInfo);
		o.put("msgid",smsContent.get("msgid").toString());
		if(!ToolsUtil.checkStringIsNull(respInfo)){
			o.put("result", "0");
			o.put("result", "提交成功");
		}else{
			o.put("result", "9999");
			o.put("result", "提交失败");
		}
		String resultInfo = JSONObject.fromObject(o).toString();

		//更新数据库
		/*
		try{
			updateSmsInfo(respInfo,smsContent,null);
		}catch(Exception e){
			logger.info("****1000*********UPDATE DB Exception ******");
			e.printStackTrace();
		}
		
		if(ToolsUtil.checkStringIsNull(respInfo)){
			 return flag;
		}else{
			//调用联通接口来获取短信是否成功发送
			 resp =getSmsSendResult(respInfo);	
		}
		
		//更新短信表的状态
		if(null == resp){
			logger.info("*****1001********The received message is empty**************");
		}else{
			try{
				updateSmsInfo("66065918",smsContent,resp);
			}catch(Exception e){
				logger.info("*************UPDATE DB Exception ******");
				e.printStackTrace();
			}
			
		}*/
		
		return resultInfo;
	}
	/**
	 * 通过smsMsid来取得短信发送的结果
	 * 
	 */
	public String getSendSmsReprot(String smsMsids){
		List<String> smsMsid = new ArrayList<String>();
		smsMsid.add(smsMsids);
		GetSMSDetailsResponse resp = null;
		String messageId = ZkGenerateSeq.get8IdSeq(SeqID.USER_ID);
	
		try {
			LaoOperatordealLog log = new LaoOperatordealLog();
			SOAPMessage soapMessage = getSMSDetailsService.createRequest(messageId,smsMsid);		
			soapMessage = getSMSDetailsService.secureMessage(soapMessage, getSMSDetailsService.getUsername(), getSMSDetailsService.getPasswd());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapMessage.writeTo(out);
			String reqMsgXml = out.toString("UTF-8");
			logger.info("SendSMSService request:"+reqMsgXml);
			log.setInputParameters(reqMsgXml);
			log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
			log.setIccid(smsMsids);
			log.setCreateDate(new Date());
			log.setOperatorType("2");
			SOAPConnection connection = getSMSDetailsService.getConnectionFactory().createConnection();
			soapMessage = connection.call(soapMessage, new URL(getSMSDetailsService.getUrl()));
			if(soapMessage.getSOAPBody().hasFault()){
				log.setResultCode("8888");
				log.setResultInfo("返回报文为NULL");
				log.setSuccess("1");
				log.setOutputParameters("");			
			}else{
				resp = (GetSMSDetailsResponse)getSMSDetailsService.parseMessage(soapMessage);
				SmsMessage sm = resp.getSmsMessages().get(0);
				logger.info("-------------------"+sm.getStatus());	
				logger.info("-------------------"+sm.getSmsMsgAttemptStatus());
				log.setResultCode(sm.getSmsMsgAttemptStatus());
				log.setResultInfo(sm.getStatus());
				log.setSuccess("0");
				log.setOutputParameters(JSON.toJSONString(resp));
			}
			logger.info("SendSMSService response:"+JSON.toJSONString(resp));
			laoOperatordealLogDAO.insert(log);
		} catch (IllegalArgumentException | SOAPException  | XWSSecurityException | IOException e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(resp).toString();
	}
	/**
	 * 
	 * @param args
	 * @return
	 * 发送短信
	 */	
	private String getSMSResult(Object ... args){	
		String resultInfo = "";
		SOAPMessage soapMessage;
		GetTerminalsByMsisdnResponse getSmsSend = null;
		try {
			LaoOperatordealLog log = new LaoOperatordealLog();
			soapMessage = SendSMSService.createRequest(args);
			soapMessage = SendSMSService.secureMessage(soapMessage,SendSMSService.getUsername(),SendSMSService.getPasswd());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapMessage.writeTo(out);
			String reqMsgXml = out.toString("UTF-8");
			logger.info("SendSMSService request:"+reqMsgXml);
			log.setInputParameters(reqMsgXml);
			log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
			log.setIccid(args[1].toString());
			log.setCreateDate(new Date());
			log.setOperatorType("2");
			SOAPConnection connection = SendSMSService.getConnectionFactory().createConnection();
			soapMessage = connection.call(soapMessage, new URL(SendSMSService.getUrl()));			
			if(soapMessage.getSOAPBody().hasFault()){
				log.setResultCode("9999");
				log.setResultInfo("返回报文为NULL");
				log.setSuccess("1");
				log.setOutputParameters("");			
			}else{
				ByteArrayOutputStream out1 = new ByteArrayOutputStream();
				soapMessage.writeTo(out1);
				String reqMsgXml1 = out1.toString("UTF-8");
				logger.info("SendSMSService response:"+reqMsgXml1);
				
				getSmsSend = (GetTerminalsByMsisdnResponse)SendSMSService.parseMessage(soapMessage);
				log.setResultCode("0000");
				log.setResultInfo("成功提交");
				log.setSuccess("0");
				log.setOutputParameters(JSON.toJSONString(getSmsSend));
			}
			laoOperatordealLogDAO.insert(log);
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		if(null != getSmsSend){
			resultInfo = getSmsSend.getSmsMsgId();
		}		
		return  resultInfo;
	}
	private void updateSmsInfo(String smsid,Map<String,Object> reqest,GetSMSDetailsResponse resp){
		LaoSmsInfo lsms = new LaoSmsInfo();
		if(null == resp){
			if(ToolsUtil.checkStringIsNull(smsid)){
				lsms.setOutSmsid("0000000000");
				lsms.setResultInfo("faile");
				lsms.setErrorCode("1");
				lsms.setDealTag("3");
			}else{
				lsms.setOutSmsid(smsid);
				lsms.setResultInfo("提交短信成功");
				lsms.setErrorCode("0");
				lsms.setDealTag("2");
			}
		}else{
			SmsMessage sm = resp.getSmsMessages().get(0);
			lsms.setResultInfo(sm.getSmsMsgAttemptStatus());
			lsms.setErrorCode(sm.getStatus());
			if("Received".equalsIgnoreCase(resp.getSmsMessages().get(0).getStatus())){
				lsms.setDealTag("2");
			}else{
				lsms.setDealTag("3");
			}
			
		}
		lsms.setSmsId(Long.valueOf(reqest.get("msgId").toString()));
		laoSms.updateByPrimaryKeySelective(lsms);
		
	}

}
