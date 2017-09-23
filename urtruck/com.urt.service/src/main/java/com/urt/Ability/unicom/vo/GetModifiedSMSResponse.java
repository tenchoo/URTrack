package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class GetModifiedSMSResponse extends WsResponse implements Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String iccid;
	private List<Long> smsMsgIds;

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

	public List<Long> getSmsMsgIds() {
		return smsMsgIds;
	}

	public void setSmsMsgIds(List<Long> smsMsgIds) {
		this.smsMsgIds = smsMsgIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
