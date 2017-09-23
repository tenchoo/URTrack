package com.urt.dto.esim;

import java.io.Serializable;

public class EsimInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceType;  //imei号
	private String deviceName;
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
