package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class TransferGlobalSimResponse extends WsResponse implements
		Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String globalSimTransferId;
	private String primaryIccid;
	private List<TargetSimSubscriptionInfo> targetSimSubscriptionInfo;

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

	public String getGlobalSimTransferId() {
		return globalSimTransferId;
	}

	public void setGlobalSimTransferId(String globalSimTransferId) {
		this.globalSimTransferId = globalSimTransferId;
	}

	public String getPrimaryIccid() {
		return primaryIccid;
	}

	public void setPrimaryIccid(String primaryIccid) {
		this.primaryIccid = primaryIccid;
	}

	public List<TargetSimSubscriptionInfo> getTargetSimSubscriptionInfo() {
		return targetSimSubscriptionInfo;
	}

	public void setTargetSimSubscriptionInfo(
			List<TargetSimSubscriptionInfo> targetSimSubscriptionInfo) {
		this.targetSimSubscriptionInfo = targetSimSubscriptionInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
