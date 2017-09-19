package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class CancelGlobalSimTransferResponse extends WsResponse implements
		Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String status;
	private String primaryIccid;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrimaryIccid() {
		return primaryIccid;
	}

	public void setPrimaryIccid(String primaryIccid) {
		this.primaryIccid = primaryIccid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
