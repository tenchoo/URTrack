package com.urt.Ability.collect;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public abstract class QueryTelByIccid {
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
	
	protected abstract ResultMsg queryOperatorPlan(Object... args) ;

	/**
	 * 将做真正的请求
	 * 
	 * @param args
	 * @return
	 */
	public String queryTelByIccid(String iccid) {
		String tel="";
		Long beginTime = System.currentTimeMillis();
		logger.info("................查询电话号码开始...................time:" + beginTime);
		ResultMsg resultMsg= sendMessage(iccid);		
		//记录入参
		LaoOperatordealLog log = new LaoOperatordealLog();
		log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		log.setIccid(iccid);
		log.setInputParameters(resultMsg.getInputMessage());
		
		//记录出参
		log.setOutputParameters(resultMsg.getOutMessage());
		log.setResultInfo("");
		log.setResultCode(resultMsg.getOpeartorsDealCode());
		log.setOperatorType("4");//1订购2发短信3状态变更4查询卡状态
		tel=resultMsg.getTel();
		if(resultMsg.isSuccess()){
			log.setSuccess("0");    //0成功1失败
		}else{
			log.setSuccess("1");    //0成功1失败
		}
		log.setCreateDate(new Date());
		laoOperatordealLogDAO.insert(log);
		logger.info("................查询电话号码结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return tel;
	}
	public List<Map<String, Object>> queryPlanByIccid(String iccid) {
		Long beginTime = System.currentTimeMillis();
		logger.info("................查询用户套餐开始...................time:" + beginTime);
		ResultMsg resultMsg= queryOperatorPlan(iccid);		
		//记录入参
		LaoOperatordealLog log = new LaoOperatordealLog();
		log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		log.setIccid(iccid);
		log.setInputParameters(resultMsg.getInputMessage());
		
		//记录出参
		log.setOutputParameters(resultMsg.getOutMessage());
		log.setResultInfo("");
		log.setResultCode(resultMsg.getOpeartorsDealCode());
		log.setOperatorType("4");//1订购2发短信3状态变更4查询卡状态
		if(resultMsg.isSuccess()){
			log.setSuccess("0");    //0成功1失败
		}else{
			log.setSuccess("1");    //0成功1失败
		}
		log.setCreateDate(new Date());
		laoOperatordealLogDAO.insert(log);
		logger.info("................查询用户套餐结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return resultMsg.getPlans();
	}
	protected class ResultMsg {
		private boolean success = false; //返回执行结果
		private String opeartorsDealCode; //返回执行结果信息
		private String opeartorsDealRst;//返回数据集
		private String inputMessage;
		private String outMessage;
		private String tel;
		private String planCode;
		private List<Map<String, Object>> plans;
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
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getPlanCode() {
			return planCode;
		}
		public void setPlanCode(String planCode) {
			this.planCode = planCode;
		}
		public List<Map<String, Object>> getPlans() {
			return plans;
		}
		public void setPlans(List<Map<String, Object>> plans) {
			this.plans = plans;
		}
		
	}
	
}
