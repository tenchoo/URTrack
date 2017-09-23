package com.urt.web.dmp.entity;

import java.io.Serializable;
public class CardData implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	
	//windows和Android公用属性
	private String imei = "";//IMEI号
	private String imsi = "";//IMSI号
	private String iccid = "";
	private String phoneNumber = ""; //手机号码
	private String networkOperatorName = "";//运营商名称
	private long cardAbsentTime = -1;//拔卡时间,毫秒数
	private long cardInsertTime = -1;//插卡时间，毫秒数
	//android特有属性
    private int simState = -1;//sim状态
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public int getSimState() {
		return simState;
	}
	public void setSimState(int simState) {
		this.simState = simState;
	}
	public String getNetworkOperatorName() {
		return networkOperatorName;
	}
	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getCardAbsentTime() {
		return cardAbsentTime;
	}
	public void setCardAbsentTime(long cardAbsentTime) {
		this.cardAbsentTime = cardAbsentTime;
	}
	public long getCardInsertTime() {
		return cardInsertTime;
	}
	public void setCardInsertTime(long cardInsertTime) {
		this.cardInsertTime = cardInsertTime;
	}
	@Override
	public String toString() {
		return "CardData [imei=" + imei + ", imsi=" + imsi + ", iccid=" + iccid
				+ ", phoneNumber=" + phoneNumber + ", networkOperatorName="
				+ networkOperatorName + ", cardAbsentTime=" + cardAbsentTime
				+ ", cardInsertTime=" + cardInsertTime + ", simState="
				+ simState + "]";
	}
}
