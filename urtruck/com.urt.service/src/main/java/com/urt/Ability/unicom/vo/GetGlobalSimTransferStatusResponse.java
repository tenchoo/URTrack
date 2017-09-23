package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class GetGlobalSimTransferStatusResponse extends WsResponse implements
		Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	
	private String primaryIccid;
	private String targetPartnerName;
	private String globalAllianceName;
	private String globalSimTransferType;//enumeration 	REVERT STANDARD	
	private String status;
	private String statusDetails;
	private String errorCode;
	private String errorDetails;
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

	public String getPrimaryIccid() {
		return primaryIccid;
	}

	public void setPrimaryIccid(String primaryIccid) {
		this.primaryIccid = primaryIccid;
	}

	public String getTargetPartnerName() {
		return targetPartnerName;
	}

	public void setTargetPartnerName(String targetPartnerName) {
		this.targetPartnerName = targetPartnerName;
	}

	public String getGlobalAllianceName() {
		return globalAllianceName;
	}

	public void setGlobalAllianceName(String globalAllianceName) {
		this.globalAllianceName = globalAllianceName;
	}

	public String getGlobalSimTransferType() {
		return globalSimTransferType;
	}

	public void setGlobalSimTransferType(String globalSimTransferType) {
		this.globalSimTransferType = globalSimTransferType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDetails() {
		return statusDetails;
	}

	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
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
