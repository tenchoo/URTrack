package com.urt.Ability.unicom.vo;

import java.io.Serializable;

import com.urt.Ability.unicom.util.NetworkAccessConfigEnum;

public class AccessConfig implements Serializable {

	private static final long serialVersionUID = 8761678758822479020L;
	private String iccid;
	private String nacId;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getNacId() {
		return nacId;
	}

	public void setNacId(String nacId) {
		this.nacId = nacId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApnNameByNacid() {
		if (this.getNacId().equalsIgnoreCase(
				NetworkAccessConfigEnum.APN1.getNacId())) {
			return "apn1";
		}
		if (this.getNacId().equalsIgnoreCase(
				NetworkAccessConfigEnum.APN2.getNacId())) {
			return "apn2";
		}
		if (this.getNacId().equalsIgnoreCase(
				NetworkAccessConfigEnum.APN_UNION.getNacId())) {
			return "apn1,apn2";
		}
		return null;
	}

	public static String getNacidByapnname(String apnName) {
		if ("apn1".equalsIgnoreCase(apnName))
			return NetworkAccessConfigEnum.APN1.getNacId();
		if ("apn2".equalsIgnoreCase(apnName))
			return NetworkAccessConfigEnum.APN2.getNacId();
		if ("apn1,apn2".equalsIgnoreCase(apnName))
			return NetworkAccessConfigEnum.APN_UNION.getNacId();
		return null;
	}
}
