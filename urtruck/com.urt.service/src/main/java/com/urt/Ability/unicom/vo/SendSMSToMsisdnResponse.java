package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class SendSMSToMsisdnResponse extends WsResponse implements Serializable {

	private static final long serialVersionUID = 8080789679912475971L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String smsMsgId;

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

	public String getSmsMsgId() {
		return smsMsgId;
	}

	public void setSmsMsgId(String smsMsgId) {
		this.smsMsgId = smsMsgId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
