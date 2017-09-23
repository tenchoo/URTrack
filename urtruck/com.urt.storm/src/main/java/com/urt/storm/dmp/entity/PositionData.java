package com.urt.storm.dmp.entity;

import java.io.Serializable;
public class PositionData implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	//windows和Android公用属性
	private double latitude;//纬度
	private double longtitude;//经度
	private String address; //地址文字描述
	private String errorInfo = "";//地址请求结果状态信息
    //移动国家码，中国为460
	private String mcc;
    //移动网络码，中国移动为00、02、04、07，联通为01、06、09，电信为03、05、11
	private String mnc;
    //电信基站信息
	private int mBaseStationId = -1;
	private int mSystemId = -1;
	private int mNetworkId = -1;
    //移动联通基站信息
	private int lac;//区域号
	private int cid;//小区号
	//android特有属性
	private float speed;//移动速度
	//windows特有属性
	private String registeredState = "0";//网络注册状态，是否注网
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMnc() {
		return mnc;
	}
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}
	public int getmBaseStationId() {
		return mBaseStationId;
	}
	public void setmBaseStationId(int mBaseStationId) {
		this.mBaseStationId = mBaseStationId;
	}
	public int getmSystemId() {
		return mSystemId;
	}
	public void setmSystemId(int mSystemId) {
		this.mSystemId = mSystemId;
	}
	public int getmNetworkId() {
		return mNetworkId;
	}
	public void setmNetworkId(int mNetworkId) {
		this.mNetworkId = mNetworkId;
	}
	public int getLac() {
		return lac;
	}
	public void setLac(int lac) {
		this.lac = lac;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public String getRegisteredState() {
		return registeredState;
	}
	public void setRegisteredState(String registeredState) {
		this.registeredState = registeredState;
	}
	@Override
	public String toString() {
		return "PositionData [latitude=" + latitude + ", longtitude="
				+ longtitude + ", address=" + address + ", errorInfo="
				+ errorInfo + ", mcc=" + mcc + ", mnc=" + mnc
				+ ", mBaseStationId=" + mBaseStationId + ", mSystemId="
				+ mSystemId + ", mNetworkId=" + mNetworkId + ", lac=" + lac
				+ ", cid=" + cid + ", speed=" + speed + ", registeredState="
				+ registeredState + "]";
	}
	
}
