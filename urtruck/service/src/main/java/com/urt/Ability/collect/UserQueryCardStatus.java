package com.urt.Ability.collect;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.CardStatusDto;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 查询卡状态操作
 * @author zhaoyf
 *
 */

public abstract class UserQueryCardStatus {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	/**
	 * 此方法用于推送参数,记录入参
	 *
	 * @param args
	 * @return
	 */
	protected abstract ResultMsg sendMessage(Object... args);

	/**
	 * 将做真正的请求
	 * 
	 * @param args
	 * @return
	 */
	public CardStatusDto queryCardStatus(String iccid) {
		Long beginTime = System.currentTimeMillis();
		logger.info("................查询卡状态开始...................time:" + beginTime);
		ResultMsg resultMsg= sendMessage(iccid);		
		CardStatusDto cardStatus = new CardStatusDto();
		LaoOperatordealLog log = new LaoOperatordealLog();		
		//记录入参
		log = new LaoOperatordealLog();
		log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		log.setIccid(iccid);
		log.setInputParameters(resultMsg.getInputMessage());
		
		//记录出参
		log.setOutputParameters(resultMsg.getOutMessage());
		log.setResultInfo(resultMsg.getOpeartorsDealRst().toString());
		log.setResultCode(resultMsg.getOpeartorsDealCode());
		log.setOperatorType("4");//1订购2发短信3状态变更4查询卡状态
		cardStatus.setCardStatus(resultMsg.getStatus());
		if(resultMsg.isSuccess()){
			log.setSuccess("0");    //0成功1失败
		}else{
			log.setSuccess("1");    //0成功1失败
		}
		log.setCreateDate(new Date());
		laoOperatordealLogDAO.insert(log);
		logger.info("................查询卡状态结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return cardStatus;
	}
	
	protected class ResultMsg {
		private boolean success = false; //返回执行结果
		private String opeartorsDealCode; //返回执行结果信息
		private String opeartorsDealRst;//返回数据集
		private String inputMessage;
		private String outMessage;
		private String status; // 查询到的卡状态
		private String tel;
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
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		
		
	}
}
