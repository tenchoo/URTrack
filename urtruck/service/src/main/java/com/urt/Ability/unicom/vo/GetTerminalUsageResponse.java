package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class GetTerminalUsageResponse extends WsResponse implements
		Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6472558625297175171L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String iccid;
	private String totalDataVolume;// 流量使用量
	private String billableDataVolume;
	private String cycleStartDate;
	private String billable;
	private String totalSMSVolume;
	private String totalVoiceVolume;
	private String billableSMSVolume;
	private String billableVoiceVolume;
	private String ratePlan;
	private String eventsUsage;
	private String totalEvents;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getIccid() {
		return iccid;
	}


	public void setIccid(String iccid) {
		this.iccid = iccid;
	}


	public String getTotalDataVolume() {
		return totalDataVolume;
	}


	public void setTotalDataVolume(String totalDataVolume) {
		this.totalDataVolume = totalDataVolume;
	}


	public String getBillableDataVolume() {
		return billableDataVolume;
	}


	public void setBillableDataVolume(String billableDataVolume) {
		this.billableDataVolume = billableDataVolume;
	}


	public String getCycleStartDate() {
		return cycleStartDate;
	}


	public void setCycleStartDate(String cycleStartDate) {
		this.cycleStartDate = cycleStartDate;
	}


	public String getBillable() {
		return billable;
	}


	public void setBillable(String billable) {
		this.billable = billable;
	}


	public String getTotalSMSVolume() {
		return totalSMSVolume;
	}


	public void setTotalSMSVolume(String totalSMSVolume) {
		this.totalSMSVolume = totalSMSVolume;
	}


	public String getTotalVoiceVolume() {
		return totalVoiceVolume;
	}


	public void setTotalVoiceVolume(String totalVoiceVolume) {
		this.totalVoiceVolume = totalVoiceVolume;
	}


	public String getBillableSMSVolume() {
		return billableSMSVolume;
	}


	public void setBillableSMSVolume(String billableSMSVolume) {
		this.billableSMSVolume = billableSMSVolume;
	}


	public String getBillableVoiceVolume() {
		return billableVoiceVolume;
	}


	public void setBillableVoiceVolume(String billableVoiceVolume) {
		this.billableVoiceVolume = billableVoiceVolume;
	}


	public String getRatePlan() {
		return ratePlan;
	}


	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}


	public String getEventsUsage() {
		return eventsUsage;
	}


	public void setEventsUsage(String eventsUsage) {
		this.eventsUsage = eventsUsage;
	}


	public String getTotalEvents() {
		return totalEvents;
	}


	public void setTotalEvents(String totalEvents) {
		this.totalEvents = totalEvents;
	}


	@Override
	public String toString() {
		return "GetTerminalUsageResponse [correlationId="+ correlationId 
				+ ", version=" + version + ", build=" + build
				+ ", timestamp=" + timestamp +", iccid=" + iccid
				+ ", totalDataVolume=" + totalDataVolume
				+ ", billableDataVolume" + billableDataVolume
				+ ", cycleStartDate=" + cycleStartDate
				+ ", billable=" + billable
				+ ", totalSMSVolume=" + totalSMSVolume
				+ ", totalVoiceVolume=" + totalVoiceVolume 
				+ ", billableSMSVolume=" + billableSMSVolume
				+ ", billableVoiceVolume=" + billableVoiceVolume
				+ ", ratePlan=" + ratePlan + ",eventsUsage=" + eventsUsage
				+ ", totalEvents=" + totalEvents
				+ "]"
				;
	}
}
