package com.urt.po;

import java.io.Serializable;

public class IccidLibrary implements Serializable {
    private double id;

    private String iccid;

    private String devicetype;

    private String privatekey;
    
    /**卡的细分**/
	private String cardType;
	/**赠送流量**/
	private String rate;

    private static final long serialVersionUID = 1L;

   
	/**
	 * @return the id
	 */
	public double getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(double id) {
		this.id = id;
	}

	public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey == null ? null : privatekey.trim();
    }

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "IccidLibrary [id=" + id + ", iccid=" + iccid + ", devicetype="
				+ devicetype + ", privatekey=" + privatekey + ", cardType="
				+ cardType + ", rate=" + rate + "]";
	}
    
}