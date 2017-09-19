package com.urt.dto.http;

import java.io.Serializable;
import java.util.List;

public class QuerySms implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String respCode;
	public String respDesc;
	public String totalNum;
	public String smsInfo;
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
	
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	
	public String getSmsInfo() {
		return smsInfo;
	}
	public void setSmsInfo(String smsInfo) {
		this.smsInfo = smsInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
