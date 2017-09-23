package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class TerminalDetailRating implements Serializable{

	
	private static final long serialVersionUID = 21512521265216L;
	
	private String termStartDate; //当前包的开始日期
	private String termEndDate;   //当前包结束日期
	private String totalPrimaryIncludedData; //当前包的总流量
	private String primaryDataRemaining;//当前包已使用量
	private String renewalRatePlan; //当前包名称
	
	
	public String getRenewalRatePlan() {
		return renewalRatePlan;
	}
	public void setRenewalRatePlan(String renewalRatePlan) {
		this.renewalRatePlan = renewalRatePlan;
	}
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
	
	

}
