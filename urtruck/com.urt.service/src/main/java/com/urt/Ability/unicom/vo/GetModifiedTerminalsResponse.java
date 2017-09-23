package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class GetModifiedTerminalsResponse extends WsResponse implements
		Serializable {
	private static final long serialVersionUID = 22364638247352987L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private List<String> iccids;

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

	public List<String> getIccids() {
		return iccids;
	}

	public void setIccids(List<String> iccids) {
		this.iccids = iccids;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
