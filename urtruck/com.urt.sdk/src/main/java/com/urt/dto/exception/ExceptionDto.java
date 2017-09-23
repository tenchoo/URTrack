package com.urt.dto.exception;

import java.io.Serializable;

public class ExceptionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String excpName; 		//异常别名
	private String tradeId;			//订单流水号
	private String batchId;			//批量流水号
	private String batchdetailId;	//批量明细流水号
	private String resultInfo;		//处理结果信息
	
	private String excpId;			//业务流水号
	private String tradeTypeCode;	//业务类型
	private String doneTimes;		//首次入表值为0
	/**
	 * @return the excpName
	 */
	public String getExcpName() {
		return excpName;
	}
	/**
	 * @param excpName the excpName to set
	 */
	public void setExcpName(String excpName) {
		this.excpName = excpName;
	}
	/**
	 * @return the tradeId
	 */
	public String getTradeId() {
		return tradeId;
	}
	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the batchdetailId
	 */
	public String getBatchdetailId() {
		return batchdetailId;
	}
	/**
	 * @param batchdetailId the batchdetailId to set
	 */
	public void setBatchdetailId(String batchdetailId) {
		this.batchdetailId = batchdetailId;
	}
	/**
	 * @return the resultInfo
	 */
	public String getResultInfo() {
		return resultInfo;
	}
	/**
	 * @param resultInfo the resultInfo to set
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	/**
	 * @return the excpId
	 */
	public String getExcpId() {
		return excpId;
	}
	/**
	 * @param excpId the excpId to set
	 */
	public void setExcpId(String excpId) {
		this.excpId = excpId;
	}
	/**
	 * @return the tradeTypeCode
	 */
	public String getTradeTypeCode() {
		return tradeTypeCode;
	}
	/**
	 * @param tradeTypeCode the tradeTypeCode to set
	 */
	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}
	/**
	 * @return the doneTimes
	 */
	public String getDoneTimes() {
		return doneTimes;
	}
	/**
	 * @param doneTimes the doneTimes to set
	 */
	public void setDoneTimes(String doneTimes) {
		this.doneTimes = doneTimes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExceptionDto [excpName=" + excpName + ", tradeId=" + tradeId
				+ ", batchId=" + batchId + ", batchdetailId=" + batchdetailId
				+ ", resultInfo=" + resultInfo + ", excpId=" + excpId
				+ ", tradeTypeCode=" + tradeTypeCode + ", doneTimes="
				+ doneTimes + "]";
	}
	
}
