package com.urt.dto.grpnet;

import java.io.Serializable;

public class GetUserDiscntInfoReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tradeId;     // 业务流水号
	private String phoneNo;     // 业务号码
	private String phoneType;   // 业务号码类型
	private String operDepart;  // 业务受理地点
	private String operator;    // 业务受理人
	private String discntId;    // 优惠id
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getOperDepart() {
		return operDepart;
	}
	public void setOperDepart(String operDepart) {
		this.operDepart = operDepart;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDiscntId() {
		return discntId;
	}
	public void setDiscntId(String discntId) {
		this.discntId = discntId;
	}
	
}
