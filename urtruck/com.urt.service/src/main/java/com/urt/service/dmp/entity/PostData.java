package com.urt.service.dmp.entity;

import java.io.Serializable;

public class PostData implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	
	
	private DeviceData deviceData;//设备信息
	private CardData cardData;//卡信息
	private PositionData positionData;//位置信息
	private int triggedReason;//触发消息发送的事件类型 0定时周期性发送事件，1插入卡发送事件，2 拔卡发送信息事件，3位置变化发送信息事件
	
	
	public DeviceData getDeviceData() {
		return deviceData;
	}
	public void setDeviceData(DeviceData deviceData) {
		this.deviceData = deviceData;
	}
	public CardData getCardData() {
		return cardData;
	}
	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}
	public PositionData getPositionData() {
		return positionData;
	}
	public void setPositionData(PositionData positionData) {
		this.positionData = positionData;
	}
	public int getTriggedReason() {
		return triggedReason;
	}
	public void setTriggedReason(int triggedReason) {
		this.triggedReason = triggedReason;
	}
	@Override
	public String toString() {
		return "PostData [deviceData=" + deviceData + ", cardData=" + cardData
				+ ", positionData=" + positionData + ", triggedReason="
				+ triggedReason + "]";
	}
}
