package com.urt.dto.grpnet;

import java.io.Serializable;
import java.util.List;

public class GetUserDiscntInfoRsp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ReturnCode;
	private String TradeId;
	private List<UserDiscntList> UserDiscntList;
	
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	public String getTradeId() {
		return TradeId;
	}
	public void setTradeId(String tradeId) {
		TradeId = tradeId;
	}
	public List<UserDiscntList> getUserDiscntList() {
		return UserDiscntList;
	}
	public void setUserDiscntList(List<UserDiscntList> userDiscntList) {
		UserDiscntList = userDiscntList;
	}
	
}
