package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class TerminalDetail implements Serializable {

	private static final long serialVersionUID = -7108154893365853515L;
	private String iccid;
	private String suspended;
	/**	资费计划名称*/
	private String ratePlan;
	/**	资费计划状态	*/
	private String status;
	/**	当月用量	*/
	private String monthToDateUsage;
	private String overageLimitReached;
	private String overageLimitOverride;
	private String dateActivated;
	private String dateAdded;
	private String dateModified;
	private String dateShipped;
	/**	当月数据用量	*/
	private String monthToDateDataUsage;
	/**	当月短信用量	*/
	private String monthToDateSMSUsage;
	/**	当月语音用量	*/
	private String monthToDateVoiceUsage;
	
	private String msisdn;
	private String accountId;
	private String version;
	private String imei;
	private String imsi;
	private String renewalPolicy;//续约模式
	
	
	private List<TerminalDetailRating> listRating;
	
	
	
	
	public List<TerminalDetailRating> getListRating() {
		return listRating;
	}

	public void setListRating(List<TerminalDetailRating> listRating) {
		this.listRating = listRating;
	}

	private List<TerminalDetailRating> rating;
	

	public List<TerminalDetailRating> getRating() {
		return rating;
	}

	public void setRating(List<TerminalDetailRating> rating) {
		this.rating = rating;
	}

	public String getRenewalPolicy() {
		return renewalPolicy;
	}

	public void setRenewalPolicy(String renewalPolicy) {
		this.renewalPolicy = renewalPolicy;
	}

	public String getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getSuspended() {
		return suspended;
	}

	public void setSuspended(String suspended) {
		this.suspended = suspended;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonthToDateUsage() {
		return monthToDateUsage;
	}

	public void setMonthToDateUsage(String monthToDateUsage) {
		this.monthToDateUsage = monthToDateUsage;
	}

	public String getOverageLimitReached() {
		return overageLimitReached;
	}

	public void setOverageLimitReached(String overageLimitReached) {
		this.overageLimitReached = overageLimitReached;
	}

	public String getOverageLimitOverride() {
		return overageLimitOverride;
	}

	public void setOverageLimitOverride(String overageLimitOverride) {
		this.overageLimitOverride = overageLimitOverride;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getDateShipped() {
		return dateShipped;
	}

	public void setDateShipped(String dateShipped) {
		this.dateShipped = dateShipped;
	}

	public String getMonthToDateDataUsage() {
		return monthToDateDataUsage;
	}

	public void setMonthToDateDataUsage(String monthToDateDataUsage) {
		this.monthToDateDataUsage = monthToDateDataUsage;
	}

	public String getMonthToDateSMSUsage() {
		return monthToDateSMSUsage;
	}

	public void setMonthToDateSMSUsage(String monthToDateSMSUsage) {
		this.monthToDateSMSUsage = monthToDateSMSUsage;
	}

	public String getMonthToDateVoiceUsage() {
		return monthToDateVoiceUsage;
	}

	public void setMonthToDateVoiceUsage(String monthToDateVoiceUsage) {
		this.monthToDateVoiceUsage = monthToDateVoiceUsage;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	};
}
