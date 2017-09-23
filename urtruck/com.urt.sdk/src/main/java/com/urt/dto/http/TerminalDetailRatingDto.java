package com.urt.dto.http;

import java.io.Serializable;

public class TerminalDetailRatingDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5354364657575721790L;
	
	private String respCode;
	private String respDesc;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	private String termStartDate; //当前包的开始日期
	private String termEndDate;   //当前包结束日期
	private String totalPrimaryIncludedData; //当前包的总流量
	private String primaryDataRemaining;//当前包已使用量
	private String renewalRatePlan; //当前包名称
	private String currentSurplus;
	public String getTermStartDate() {
		return termStartDate;
	}
	public void setTermStartDate(String termStartDate) {
		this.termStartDate = termStartDate;
	}
	public String getTermEndDate() {
		return termEndDate;
	}
	public void setTermEndDate(String termEndDate) {
		this.termEndDate = termEndDate;
	}
	public String getTotalPrimaryIncludedData() {
		return totalPrimaryIncludedData;
	}
	public void setTotalPrimaryIncludedData(String totalPrimaryIncludedData) {
		this.totalPrimaryIncludedData = totalPrimaryIncludedData;
	}
	public String getPrimaryDataRemaining() {
		return primaryDataRemaining;
	}
	public void setPrimaryDataRemaining(String primaryDataRemaining) {
		this.primaryDataRemaining = primaryDataRemaining;
	}
	public String getRenewalRatePlan() {
		return renewalRatePlan;
	}
	public void setRenewalRatePlan(String renewalRatePlan) {
		this.renewalRatePlan = renewalRatePlan;
	}
	public String getCurrentSurplus() {
		return currentSurplus;
	}
	public void setCurrentSurplus(String currentSurplus) {
		this.currentSurplus = currentSurplus;
	}
	
	
}
