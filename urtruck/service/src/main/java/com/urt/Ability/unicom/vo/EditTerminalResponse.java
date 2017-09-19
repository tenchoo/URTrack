package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class EditTerminalResponse extends WsResponse implements Serializable {

	private static final long serialVersionUID = -523165435159647518L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String iccid;
	private String effectiveDate;

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

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
