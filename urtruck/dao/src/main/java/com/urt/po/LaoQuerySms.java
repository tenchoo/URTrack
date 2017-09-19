package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoQuerySms implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long deliverId;

    private String msisdn;

    private String destNumber;

    private String smsContent;

    private Date sendDate;

    private Long smsId;

    private Long pushState;

    private String pushDesc;

	public Long getDeliverId() {
		return deliverId;
	}

	public void setDeliverId(Long deliverId) {
		this.deliverId = deliverId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public Long getPushState() {
		return pushState;
	}

	public void setPushState(Long pushState) {
		this.pushState = pushState;
	}

	public String getPushDesc() {
		return pushDesc;
	}

	public void setPushDesc(String pushDesc) {
		this.pushDesc = pushDesc;
	}

    
}
