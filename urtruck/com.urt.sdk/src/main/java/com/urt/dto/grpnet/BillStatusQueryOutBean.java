package com.urt.dto.grpnet;

import java.io.Serializable;

public class BillStatusQueryOutBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resCode;
	private String resMsg;
	private GetUserDiscntInfoRsp GetUserDiscntInfoRsp;
	
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public GetUserDiscntInfoRsp getGetUserDiscntInfoRsp() {
		return GetUserDiscntInfoRsp;
	}
	public void setGetUserDiscntInfoRsp(GetUserDiscntInfoRsp getUserDiscntInfoRsp) {
		GetUserDiscntInfoRsp = getUserDiscntInfoRsp;
	}

}
