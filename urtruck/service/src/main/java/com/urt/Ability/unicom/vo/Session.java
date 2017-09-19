package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class Session implements Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String iccid;
	private String ipAddress;
	private String dateSessionStarted;
	private String dateSessionEnded;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDateSessionStarted() {
		return dateSessionStarted;
	}

	public void setDateSessionStarted(String dateSessionStarted) {
		this.dateSessionStarted = dateSessionStarted;
	}

	public String getDateSessionEnded() {
		return dateSessionEnded;
	}

	public void setDateSessionEnded(String dateSessionEnded) {
		this.dateSessionEnded = dateSessionEnded;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
