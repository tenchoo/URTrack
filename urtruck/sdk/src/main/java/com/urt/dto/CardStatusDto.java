package com.urt.dto;

import java.io.Serializable;

public class CardStatusDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5964207276721048334L;
	private String cardStatus;
	private String respCode;
	private String respDesc;
	
	
	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
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


	
	
	
}