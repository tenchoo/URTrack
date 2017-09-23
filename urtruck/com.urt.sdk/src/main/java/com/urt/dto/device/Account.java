package com.urt.dto.device;

import java.io.Serializable;
public class Account implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	private String AccountID;
	private String Username;
	private String uname;
	private String verified;
	private String wust;
	private String idcLocation;
	private String amount;
	private String DeviceID;
	private String Thirdname;
	
	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getWust() {
		return wust;
	}

	public String getIdcLocation() {
		return idcLocation;
	}

	public void setIdcLocation(String idcLocation) {
		this.idcLocation = idcLocation;
	}

	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public void setWust(String wust) {
		this.wust = wust;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getThirdname() {
		return Thirdname;
	}

	public void setThirdname(String thirdname) {
		Thirdname = thirdname;
	}
}
