package com.urt.service.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.service.GetNetworkAccessConfigService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.interfaces.User.UserQueryCardStateTest;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("userQueryCardStateTest")
@Transactional(propagation=Propagation.REQUIRED)
public class UserQueryCardStateTestImpl implements UserQueryCardStateTest{
	@Autowired
	private GetNetworkAccessConfigService getNetworkAccessConfigService;
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	private LaoOperatordealLog log;
	
	public void updateQueryCardState(String iccid){
		String messageId = ZkGenerateSeq.get8IdSeq(SeqID.ACCESSLOG_ID);
		ResultMsg msg = new ResultMsg();
		
		Long beginTime = System.currentTimeMillis();
		//log.info("................激活前查询设备状态...................time:"
				///+ beginTime);
		String nowApnName = null;

		GetNetworkAccessConfigResponse response = null;
		AccessConfig accessConfig = null;
		try {
			List<String> list = new ArrayList<String>();
			list.add(iccid);
			SOAPMessage soapMessage = getNetworkAccessConfigService.call(list,messageId);
			response = (GetNetworkAccessConfigResponse) getNetworkAccessConfigService
					.parseMessage(soapMessage);
		} catch (IOException | XWSSecurityException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonStr = JSON.toJSONString(response);
		if (response != null) {
			List<AccessConfig> list = response.getList();
			if (list != null && list.size() > 0)
				accessConfig = list.get(0);
			msg.setSuccess(true);
			msg.setOutMessage(jsonStr);
			msg.setOpeartorsDealCode("success");
			msg.setOpeartorsDealRst("0");         //0成功1失败
		}else{
			msg.setOutMessage(jsonStr);
			msg.setOpeartorsDealCode("failed");
			msg.setOpeartorsDealRst("1"); 		//0成功1失败
		}
		if (accessConfig == null) {
		//	log.info("获取资费计划异常");
			msg.setStatus("error");
		} else {
			nowApnName = accessConfig.getApnNameByNacid();
			msg.setStatus(nowApnName);
		}
		
		//记录入参
		log = new LaoOperatordealLog();
		log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		log.setIccid(iccid);
		log.setInputParameters(msg.getInputMessage());
		
		//记录出参
		log.setOutputParameters(msg.getOutMessage());
		log.setResultInfo(msg.getOpeartorsDealRst().toString());
		log.setResultCode(msg.getOpeartorsDealCode());
		log.setOperatorType("4");//1订购2发短信3状态变更4查询卡状态
		if(msg.isSuccess()){
			log.setSuccess("0");    //0成功1失败
		}else{
			log.setSuccess("1");    //0成功1失败
		}
		log.setCreateDate(new Date());
		laoOperatordealLogDAO.insert(log);
		//logger.info("................查询卡状态结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		
		//log.info("................激活前查询设备状态.......... Total cost: "
		//		+ (System.currentTimeMillis() - beginTime) / 1000.0);
	}
	
	
	protected class ResultMsg {
		private boolean success = false; //返回执行结果
		private String opeartorsDealCode; //返回执行结果信息
		private String opeartorsDealRst;//返回数据集
		private String inputMessage;
		private String outMessage;
		private String status; // 查询到的卡状态
		public ResultMsg() {
			// TODO Auto-generated constructor stub
		}
		/**
		 * @return the success
		 */
		public boolean isSuccess() {
			return success;
		}
		/**
		 * @param success the success to set
		 */
		public void setSuccess(boolean success) {
			this.success = success;
		}
		/**
		 * @return the opeartorsDealCode
		 */
		public String getOpeartorsDealCode() {
			return opeartorsDealCode;
		}
		/**
		 * @param opeartorsDealCode the opeartorsDealCode to set
		 */
		public void setOpeartorsDealCode(String opeartorsDealCode) {
			this.opeartorsDealCode = opeartorsDealCode;
		}
		
		/**
		 * @return the opeartorsDealRst
		 */
		public String getOpeartorsDealRst() {
			return opeartorsDealRst;
		}
		/**
		 * @param opeartorsDealRst the opeartorsDealRst to set
		 */
		public void setOpeartorsDealRst(String opeartorsDealRst) {
			this.opeartorsDealRst = opeartorsDealRst;
		}
		/**
		 * @return the inputMessage
		 */
		public String getInputMessage() {
			return inputMessage;
		}
		/**
		 * @param inputMessage the inputMessage to set
		 */
		public void setInputMessage(String inputMessage) {
			this.inputMessage = inputMessage;
		}
		/**
		 * @return the outMessage
		 */
		public String getOutMessage() {
			return outMessage;
		}
		/**
		 * @param outMessage the outMessage to set
		 */
		public void setOutMessage(String outMessage) {
			this.outMessage = outMessage;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
	}
}
