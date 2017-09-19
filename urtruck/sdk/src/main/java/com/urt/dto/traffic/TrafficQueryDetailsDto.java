package com.urt.dto.traffic;

import java.io.Serializable;

public class TrafficQueryDetailsDto implements Serializable {

	private static final long serialVersionUID = 810186222735993694L;

	/** 使用日期 */
	private String sessionStartTime;
	/** 流量(KB) */
	private String dataVolume;
	/** 异常信息 */
	private String expMsg;
	
	//2017年6月2日15:47:36 sunhao 
	private String duration; //使用的时间长
	private String zone;     
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getSessionStartTime() {
		return sessionStartTime;
	}
	public void setSessionStartTime(String sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}
	public String getDataVolume() {
		return dataVolume;
	}
	public void setDataVolume(String dataVolume) {
		this.dataVolume = dataVolume;
	}
	public String getExpMsg() {
		return expMsg;
	}
	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}
}
