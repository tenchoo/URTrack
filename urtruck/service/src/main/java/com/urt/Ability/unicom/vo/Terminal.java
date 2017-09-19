package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class Terminal implements Serializable {

	private static final long serialVersionUID = -7108154893365853515L;
	private String iccid;
	private String msisdn;
	private String imsi;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	

}
