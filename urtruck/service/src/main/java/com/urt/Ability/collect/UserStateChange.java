package com.urt.Ability.collect;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.miniService.authority.MiniUserArchivingServiceImpl;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoUserSvcstate;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;


public abstract class UserStateChange   {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	
	
	@Autowired
	private LaoUserSvcstateMapper laoUserSvcstateDAO;
	
	@Autowired
	private MiniUserArchivingServiceImpl miniUserArchivingService;
	
	@Autowired
	private ServiceStatusService serviceStatusService;
	/**
	 * 此方法用于推送参数,记录入参
	 *
	 * @param args
	 * @return
	 */
	protected  abstract ResultMsg sendMessage(Object... args);

	
	public boolean stateChange(Object... args){
		if (args == null || args.length == 0 ||args.length < 5)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		Long userId = (Long) args[1];
		Integer serviceId = (Integer) args[2];
		Date startDate = (Date) args[3];
		String newStateCode = (String) args[4];
		String tradeId = (String) args[5];
		
		//查询供应商对应的状态
		if(StringUtils.isNotBlank(newStateCode)){
			newStateCode = getNewStatus(serviceId,newStateCode);
		}
		Long beginTime = System.currentTimeMillis();
		logger.info("................状态变更开始...................time:" + beginTime);
		ResultMsg resultMsg= sendMessage(iccid,newStateCode,serviceId,tradeId);
		//记录入参
		LaoOperatordealLog log = new LaoOperatordealLog();
		log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		log.setIccid(iccid);
		log.setInputParameters(resultMsg.getInputMessage());
		
		//记录出参
		log.setOutputParameters(resultMsg.getOutMessage());
		if(resultMsg.getOpeartorsDealRst() != null)
		log.setResultInfo(resultMsg.getOpeartorsDealRst().toString());
		log.setResultCode(resultMsg.getOpeartorsDealCode());
		log.setOperatorType("3");//1订购2发短信3状态变更4查询卡状态
		if(resultMsg.isSuccess()){
			log.setSuccess("0");    //0成功1失败
		}else{
			log.setSuccess("1");    //0成功1失败
		}
		log.setCreateDate(new Date());
		laoOperatordealLogDAO.insert(log);
		
		//记录状态变更结果
		LaoUserSvcstate userSvcstate = laoUserSvcstateDAO.selectByPrimaryKey(userId, serviceId, startDate);
		if(userSvcstate != null){
			userSvcstate.setOpeartorsDealCode(resultMsg.getOpeartorsDealCode());
			userSvcstate.setOpeartorsDealNum(userSvcstate.getOpeartorsDealNum()+1);
			userSvcstate.setOpeartorsDealRst(resultMsg.getOpeartorsDealRst());
			userSvcstate.setUpdateTime(new Date());
			miniUserArchivingService.updateLaoUserSvcstate(userSvcstate);
		}
		logger.info("................状态变更结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return resultMsg.isSuccess();
	}
	
	/**
	 * 查询新状态方法
	 */
	public String getNewStatus(Integer serviceId, String statusCode) {
		ServiceStatusDto queryState = null;
		if (serviceId != -1 && StringUtils.isNotBlank(statusCode)) {
			queryState = serviceStatusService.queryState(serviceId, statusCode);
		}
		String newStateCode = null;
		if (queryState != null) {
			newStateCode = queryState.getStatechangeId();
		} else {
			logger.info("................查询将要转换的新的卡状态 错误..................."+ statusCode);
		}
		return newStateCode;
	}
	
	protected class ResultMsg {
		private boolean success = false; //返回执行结果
		private String opeartorsDealCode; //返回执行结果信息
		private String opeartorsDealRst;//返回数据集
		private String inputMessage;
		private String outMessage;
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
		
	}
	
}
