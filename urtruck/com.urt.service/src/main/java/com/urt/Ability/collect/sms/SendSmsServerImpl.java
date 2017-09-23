package com.urt.Ability.collect.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.Goods.CustomerDto;
import com.urt.dto.http.QuerySms;
import com.urt.dto.http.SendSmsDto;
import com.urt.dto.http.SmsInfo;
import com.urt.interfaces.http.SendSmsServer;
import com.urt.mapper.LaoProvideServerMapper;
import com.urt.mapper.LaoSmsDeliverMapper;
import com.urt.mapper.LaoSmsInfoMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.QuerySmsMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoProvideServer;
import com.urt.po.LaoQuerySms;
import com.urt.po.LaoSmsDeliver;
import com.urt.po.LaoSmsInfo;
import com.urt.po.LaoUser;
import com.urt.po.Operators;
import com.urt.po.Trade;
import com.urt.utils.SpringContextUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

@Service("sendSmsServer")
public class SendSmsServerImpl implements SendSmsServer{
	protected static final Logger logger = Logger.getLogger(SendSmsServerImpl.class);
	@Autowired
	LaoUserExtMapper laoUserDao;
	@Autowired
	OperatorsMapper operatorsDao;
	@Autowired
	DHSTSendSmsServerImpl dHSTSendSmsServerImpl;
	@Autowired
	QuerySmsMapper querySmsMapper;
	
	private String querySmsNum;
	
	@Autowired
	LaoProvideServerMapper resourceMap;
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.SendSmsServer#sendSMS(java.lang.String)
	 * 短信发送类 smsContent 格式
	 * mapContent.put("sendWay", operatorsId);
	   mapContent.put("number", serialNumber);
	   mapContent.put("content", content);
	   mapContent.put("msgId", smsId);
	   mapContent.put("iccid", iccid);
	 */
	public String sendSMS(Map<String,Object> smsContent) {
		logger.info("********SendSmsServerImpl.sendSMS()*******send sms begin*********************");
		SendSmsAbstract sendSmsAbstract = null;
		//参数校验
		if("1000".equals(smsContent.get("sendWay"))){
			 sendSmsAbstract = dHSTSendSmsServerImpl;
		}else{
			LaoUser laoUser = null;
				laoUser =	laoUserDao.selectByIccid(smsContent.get("iccid").toString());
			if(null ==laoUser){
				laoUser = laoUserDao.selectByMsisdn(smsContent.get("number").toString());
			}
			Operators op = operatorsDao.selectByPrimaryKey(laoUser.getOperatorsId());
			sendSmsAbstract = SpringContextUtils.getBean(op.getSendSemClass());
		}		
		String	sendSmsResp = sendSmsAbstract.sendSMS(smsContent);
		logger.info("********SendSmsServerImpl.sendSMS() resp*"+sendSmsResp);
		return sendSmsResp;
	}
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.SendSmsServer#getReport(java.lang.String, java.lang.String, java.lang.String)
	 * 获取发出去的短信是否发送成功
	 */
	public String getReport(String sendWay,String iccid,String phone){
		SendSmsAbstract sendSmsAbstract = null;
		if("1000".equals(sendWay)){
			sendSmsAbstract = dHSTSendSmsServerImpl;
		}else{
			LaoUser laoUser = null;
			laoUser =	laoUserDao.selectByIccid(iccid);
			if(null ==laoUser){
				laoUser = laoUserDao.selectByMsisdn(phone);
			}
			Operators op = operatorsDao.selectByPrimaryKey(laoUser.getOperatorsId());
			sendSmsAbstract = SpringContextUtils.getBean(op.getSendSemClass());
		}
		String rsponeInfo = sendSmsAbstract.getSendSmsReprot(iccid);
		logger.info("********SendSmsServerImpl.getReport() resp*"+rsponeInfo);
		return rsponeInfo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.SendSmsServer#sendSMS(java.util.Map)
	 * 外围系统调用发短信的接口
	 */
	@Override
	public SendSmsDto sendSMSgla(Map<String, String> reqInfo) {
		SendSmsDto smsDto = new SendSmsDto();
		String iccid = reqInfo.get(Constants.Iccid);
		String smsConent = reqInfo.get("smsConent");
		String custId = reqInfo.get(Constants.CustID);
		logger.info("****************************"+iccid);
		/*LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		int operatorsid;
		if(null == laoUser){
			smsDto.setRespCode("8888");
			smsDto.setRespDesc("fail");
			smsDto.setSendSmsTag("1");
			return smsDto;	
		}else{
			operatorsid = laoUser.getOperatorsId();
		}*/
		String number = "1064887825795";
		//String msgid = new StringBuffer().append(ToolsUtil.getId()).toString();
		/*
		 * 外围系统调用时需要将短信记录数据库
		 */
		LaoSmsInfo lsms = new LaoSmsInfo();
		/*try{*/
			/*lsms.setSmsId(Long.valueOf(msgid));
			lsms.setIccid(iccid);
			lsms.setSmsContext(smsConent);
			lsms.setSendTime(new Date());
			lsms.setSmsCorp(String.valueOf(operatorsid));//大汉三通
			lsms.setChannelCustId(Long.valueOf(reqInfo.get("custId")));
			laoSms.insert(lsms);
		
			Map<String,Object> smsReq = new HashMap<String,Object>();
			smsReq.put("sendWay",String.valueOf(operatorsid));
			smsReq.put("number", laoUser.getMsisdn());
			smsReq.put("iccid", iccid);
			smsReq.put("content", smsConent);
			smsReq.put("msgid", msgid);	
			String smsRsp = sendSMS(smsReq);
			
			JSONObject json = JSONObject.fromObject(smsRsp);
			String resultCode = json.getString("result");
			json.getString("desc");
			if("0".equals(resultCode)){
				String rspJosn = getReport(String.valueOf(operatorsid),iccid,laoUser.getMsisdn());
			}*/
		/*	boolean res = false;
			//if(!ToolsUtil.checkStringIsNull(number)){
				Date date = new Date();
				Long msgId = laoCmppService.saveMsg(smsConent, number,custId,date);
				
				res = cmppService.sendCmpp3Msg(smsConent, number);
				if(res){
					laoCmppService.updateMsgSuccess(msgId);
				}*/
			//}
			
		/*if(res){
			smsDto.setRespCode("0000");
			smsDto.setRespDesc("success");
			smsDto.setSendSmsTag("0");
		}else{
			smsDto.setRespCode("9999");
			smsDto.setRespDesc("fail");
			smsDto.setSendSmsTag("1");
		}
		}catch(Exception e){
			smsDto.setRespCode("9999");
			smsDto.setRespDesc("系统异常");
			smsDto.setSendSmsTag("1");
			e.printStackTrace();
		}*/
		return  smsDto;
	}
	/**
	 * 
	 * @param reqInfo
	 * @return 
	 * 短信查询
	 */
	public QuerySms querySms(Map<String, String> reqInfo){
		logger.info("*********"+reqInfo.values());
		String custId = reqInfo.get(Constants.CustID);
		String startDate = reqInfo.get("startDate");
		String endDate = reqInfo.get("endDate");	
		List<SmsInfo> list = new ArrayList<SmsInfo>();
		QuerySms sms=null;
		try{
			List<LaoQuerySms> querySmsInfo = querySmsMapper.selectSms(custId, startDate, endDate);
			logger.info("number:" +querySmsInfo.size());
			
			LaoProvideServer laoP = resourceMap.selectByServerName("Lao.base.sms.querySms");
			if(querySmsInfo.size() > Integer.valueOf(laoP.getParaName1())){
				sms = new QuerySms();
				sms.setRespCode("9999");
				sms.setRespDesc("pass number 5000");
				return sms;
			}
			for (LaoQuerySms laoSmsDel : querySmsInfo) {
				SmsInfo smsInfo = new SmsInfo();
				BeanMapper.copy(laoSmsDel, smsInfo);
				list.add(smsInfo);
			}
			String smsInfo = JSONArray.fromObject(list).toString();
			logger.info("****smsInfo*****"+smsInfo + smsInfo.getBytes().length);
			
			//加密加压
			String comStr = "";
			try {
				comStr = ToolsUtil.compress(smsInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("****comStr*****"+ comStr.getBytes().length);
			/*try {
				System.out.println("你好："+ToolsUtil.uncompress(comStr));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			sms = new QuerySms();
			sms.setSmsInfo(comStr);
			sms.setRespCode("0000");
			sms.setRespDesc("success");
			String num = String.valueOf(list.size());
			
			sms.setTotalNum(num);
			logger.info("****num*****"+ num);	
		}catch(Exception e){
			sms = new QuerySms();
			sms.setRespCode("9999");
			sms.setRespDesc("fail");
			e.printStackTrace();
		}
		
		return sms;
	}

}
