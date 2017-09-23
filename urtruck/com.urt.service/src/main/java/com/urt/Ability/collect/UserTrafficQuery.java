package com.urt.Ability.collect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.Operators;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 用户流量查询
 * 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class UserTrafficQuery<T> {

	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	/**
	 * 日查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract TrafficQueryResultMsg sendDayMessage(Object... args);

	/**
	 * 月查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract TrafficQueryResultMsg sendMonthMessage(Object... args);

	/**
	 * 剩余流量实时查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract TrafficQueryResultMsg sendNowMessage(Object... args);
	
	/**
	 * 批量查询流量 
	 * 
	 * @param args
	 * @return
	 */
	public abstract TrafficQueryResultMsg sendBatchQueryMessage(Object... args);
	
	
	/**
	 * 调用接口日查询流量使用情况
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public LaoTrafficDetailDto sendBatchMessage(String iccid, Operators op) throws Exception {
		LaoTrafficDetailDto laoTrafficDetailDto = null;
		UserTrafficQuery<T>.TrafficQueryResultMsg trafficQueryResultMsg = sendBatchQueryMessage(iccid);
		laoTrafficDetailDto = trafficQueryResultMsg.getLaoTrafficDetailDto();
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setIccid(iccid);
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setInputParameters(trafficQueryResultMsg.getInputMessage());
		logger.setOutputParameters(trafficQueryResultMsg.getOutMessage());
		logger.setResultInfo(trafficQueryResultMsg.getOpeartorsDealRst());
		logger.setResultCode(trafficQueryResultMsg.getOpeartorsDealCode());
		if (op.getOperatorsId() != null) {
			logger.setOperatorId(op.getOperatorsId().toString());
		}
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		if (trafficQueryResultMsg.isSuccess()) {
			logger.setSuccess("0"); // 0成功1失败
		} else {
			logger.setSuccess("1"); // 0成功1失败
		}
		logger.setCreateDate(new Date());
		try {
			// 记录传入传出参数
			laoOperatordealLogDAO.insertSelective(logger);
		} catch (Exception e) {
			e.printStackTrace();
			return laoTrafficDetailDto;
		}
		return laoTrafficDetailDto;
	}
	
	/**
	 * 调用接口日查询流量使用情况
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public List<TrafficQueryDetailsDto> trafficDayQuery(String iccid,
			String date, Operators op) throws Exception {
		List<TrafficQueryDetailsDto> listDto = null;
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		UserTrafficQuery<T>.TrafficQueryResultMsg trafficQueryResultMsg = sendDayMessage(
				iccid, messageId, "0", date);// totalPages=0,联通的查询出所有记录
		listDto = trafficQueryResultMsg.getListDto();
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setIccid(iccid);
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setInputParameters(trafficQueryResultMsg.getInputMessage());
		logger.setOutputParameters(trafficQueryResultMsg.getOutMessage());
		logger.setResultInfo(trafficQueryResultMsg.getOpeartorsDealRst());
		logger.setResultCode(trafficQueryResultMsg.getOpeartorsDealCode());
		if (op.getOperatorsId() != null) {
			logger.setOperatorId(op.getOperatorsId().toString());
		}
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		if (trafficQueryResultMsg.isSuccess()) {
			logger.setSuccess("0"); // 0成功1失败
		} else {
			logger.setSuccess("1"); // 0成功1失败
		}
		logger.setCreateDate(new Date());
		try {
			// 记录传入传出参数
			laoOperatordealLogDAO.insertSelective(logger);
		} catch (Exception e) {
			e.printStackTrace();
			return listDto;
		}
		return listDto;
	}

	/**
	 * 调用接口月查询流量使用情况
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<TrafficQueryDetailsDto> trafficMonthQuery(String iccid,
			String date, Operators op) throws Exception {
		List<TrafficQueryDetailsDto> listDto = null;
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		UserTrafficQuery<T>.TrafficQueryResultMsg trafficQueryResultMsg = sendMonthMessage(
				iccid, messageId, "0", date);// totalPages=0,联通的查询出所有记录
		listDto = trafficQueryResultMsg.getListDto();
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setIccid(iccid);
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setInputParameters(trafficQueryResultMsg.getInputMessage());
		logger.setOutputParameters(trafficQueryResultMsg.getOutMessage());
		logger.setResultInfo(trafficQueryResultMsg.getOpeartorsDealRst());
		logger.setResultCode(trafficQueryResultMsg.getOpeartorsDealCode());

		if (op.getOperatorsId() != null) {
			logger.setOperatorId(op.getOperatorsId().toString());
		}
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		if (trafficQueryResultMsg.isSuccess()) {
			logger.setSuccess("0"); // 0成功1失败
		} else {
			logger.setSuccess("1"); // 0成功1失败
		}
		logger.setCreateDate(new Date());
		try {
			// 记录传入传出参数
			laoOperatordealLogDAO.insertSelective(logger);
		} catch (Exception e) {
			return listDto;
		}
		return listDto;
	}

	/**
	 * 调用接口实时查询剩余流量使用情况
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public TrafficQueryNowDto trafficNowQuery(String iccid, Operators op)throws Exception {
		TrafficQueryNowDto trafficDto = null;
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		UserTrafficQuery<T>.TrafficQueryResultMsg trafficQueryResultMsg = sendNowMessage(iccid, messageId);
		trafficDto = trafficQueryResultMsg.getTrafficDto();
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setIccid(iccid);
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setInputParameters(trafficQueryResultMsg.getInputMessage());
		logger.setOutputParameters(trafficQueryResultMsg.getOutMessage());
		logger.setResultInfo(trafficQueryResultMsg.getOpeartorsDealRst());
		logger.setResultCode(trafficQueryResultMsg.getOpeartorsDealCode());
		if (op.getOperatorsId() != null) {
			logger.setOperatorId(op.getOperatorsId()+"");
		}
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		if (trafficQueryResultMsg.isSuccess()) {
			logger.setSuccess("0"); // 0成功1失败
		} else {
			logger.setSuccess("1"); // 0成功1失败
		}
		logger.setCreateDate(new Date());
		// 记录传入传出参数
		laoOperatordealLogDAO.insertSelective(logger);
		return trafficDto;
	}

	protected class TrafficQueryResultMsg {
		private boolean success = false; // 返回执行结果
		private String opeartorsDealCode; // 返回执行结果信息
		private String opeartorsDealRst;// 返回数据集
		private String inputMessage;
		private String outMessage;
		private TrafficQueryNowDto trafficDto;
		private LaoTrafficDetailDto laoTrafficDetailDto;
		private List<TrafficQueryDetailsDto> listDto;
		private List<PackagePlanDto> listPackagePlanDto;

		
		public LaoTrafficDetailDto getLaoTrafficDetailDto() {
			return laoTrafficDetailDto;
		}

		public void setLaoTrafficDetailDto(LaoTrafficDetailDto laoTrafficDetailDto) {
			this.laoTrafficDetailDto = laoTrafficDetailDto;
		}

		public List<TrafficQueryDetailsDto> getListDto() {
			return listDto;
		}

		public void setListDto(List<TrafficQueryDetailsDto> listDto) {
			this.listDto = listDto;
		}

		public TrafficQueryResultMsg() {
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getOpeartorsDealCode() {
			return opeartorsDealCode;
		}

		public void setOpeartorsDealCode(String opeartorsDealCode) {
			this.opeartorsDealCode = opeartorsDealCode;
		}

		public String getOpeartorsDealRst() {
			return opeartorsDealRst;
		}

		public void setOpeartorsDealRst(String opeartorsDealRst) {
			this.opeartorsDealRst = opeartorsDealRst;
		}

		public String getInputMessage() {
			return inputMessage;
		}

		public void setInputMessage(String inputMessage) {
			this.inputMessage = inputMessage;
		}

		public String getOutMessage() {
			return outMessage;
		}

		public void setOutMessage(String outMessage) {
			this.outMessage = outMessage;
		}

		public TrafficQueryNowDto getTrafficDto() {
			return trafficDto;
		}

		public void setTrafficDto(TrafficQueryNowDto trafficDto) {
			this.trafficDto = trafficDto;
		}

		public List<PackagePlanDto> getListPackagePlanDto() {
			return listPackagePlanDto;
		}

		public void setListPackagePlanDto(List<PackagePlanDto> listPackagePlanDto) {
			this.listPackagePlanDto = listPackagePlanDto;
		}

	}
}
