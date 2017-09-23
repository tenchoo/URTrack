package com.urt.dto.esim;

import java.io.Serializable;

public class EsimDeviceInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceType;
	private String useFlow;
	private String operator;
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getUseFlow() {
		return useFlow;
	}
	public void setUseFlow(String useFlow) {
		this.useFlow = useFlow;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
