package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class EditNetworkAccessConfigResponse extends WsResponse implements
		Serializable {

	private static final long serialVersionUID = 5484435781656681830L;
	private String iccid;
	private String correlationId;
	private String version;
	private String timestamp;
	private String effectiveDate;
	private String build;
	
	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
}
