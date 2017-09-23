package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class TargetSimSubscriptionInfo implements Serializable {

	private static final long serialVersionUID = 22364638247352987L;
	private String targetIccid;
	private String targetMsisdn;
	private String targetImsi;

	public String getTargetIccid() {
		return targetIccid;
	}

	public void setTargetIccid(String targetIccid) {
		this.targetIccid = targetIccid;
	}

	public String getTargetMsisdn() {
		return targetMsisdn;
	}

	public void setTargetMsisdn(String targetMsisdn) {
		this.targetMsisdn = targetMsisdn;
	}

	public String getTargetImsi() {
		return targetImsi;
	}

	public void setTargetImsi(String targetImsi) {
		this.targetImsi = targetImsi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
