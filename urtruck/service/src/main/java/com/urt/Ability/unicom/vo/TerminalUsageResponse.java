package com.urt.Ability.unicom.vo;

import java.io.Serializable;

/**
 * 资费详情
 * @author zhaoyf
 *
 */
public class TerminalUsageResponse implements Serializable {

	private static final long serialVersionUID = 810186222735993694L;
	
	private String totalPages;
	private String iccid;
	private String cycleStartDate;
	private String terminalId;
	private String endConsumerId;
	private String customer;
	private String billable;
	private String zone;
	private String sessionStartTime;
	private String duration;
	private String dataVolume;
	private String countryCode;
	private String serviceType;
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getCycleStartDate() {
		return cycleStartDate;
	}
	public void setCycleStartDate(String cycleStartDate) {
		this.cycleStartDate = cycleStartDate;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getEndConsumerId() {
		return endConsumerId;
	}
	public void setEndConsumerId(String endConsumerId) {
		this.endConsumerId = endConsumerId;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getBillable() {
		return billable;
	}
	public void setBillable(String billable) {
		this.billable = billable;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getSessionStartTime() {
		return sessionStartTime;
	}
	public void setSessionStartTime(String sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDataVolume() {
		return dataVolume;
	}
	public void setDataVolume(String dataVolume) {
		this.dataVolume = dataVolume;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	

}
