package com.urt.dto.http;

import java.io.Serializable;

public class SendSmsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String respCode;
	private String respDesc;
	private String sendSmsTag;
	private String smsId;
	
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
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
	public String getSendSmsTag() {
		return sendSmsTag;
	}
	public void setSendSmsTag(String sendSmsTag) {
		this.sendSmsTag = sendSmsTag;
	}
	
}
