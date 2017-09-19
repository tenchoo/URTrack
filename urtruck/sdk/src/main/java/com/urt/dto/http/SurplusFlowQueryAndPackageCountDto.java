package com.urt.dto.http;

import java.io.Serializable;

public class SurplusFlowQueryAndPackageCountDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String respDesc;
	private String surplusFlowValue;
	private String currentPackExpirationTime;
	private String PackCount;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getSurplusFlowValue() {
		return surplusFlowValue;
	}
	public void setSurplusFlowValue(String surplusFlowValue) {
		this.surplusFlowValue = surplusFlowValue;
	}
	public String getCurrentPackExpirationTime() {
		return currentPackExpirationTime;
	}
	public void setCurrentPackExpirationTime(String currentPackExpirationTime) {
		this.currentPackExpirationTime = currentPackExpirationTime;
	}
	public String getPackCount() {
		return PackCount;
	}
	public void setPackCount(String packCount) {
		PackCount = packCount;
	}
	
}
