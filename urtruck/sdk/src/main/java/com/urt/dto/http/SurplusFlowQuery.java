package com.urt.dto.http;

import java.io.Serializable;

public class SurplusFlowQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String respDesc;
	private String surplusFlowValue;
	private String userFlowValue;  //已使用流量
	
	public String getUserFlowValue() {
		return userFlowValue;
	}
	public void setUserFlowValue(String userFlowValue) {
		this.userFlowValue = userFlowValue;
	}

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

	

}
