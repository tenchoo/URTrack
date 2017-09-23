package com.urt.Ability.unicom.jsonbean;

public class UnicomNotifyChangeHead {
	private String eventId;
	private String eventType;
	private String timestamp;
	private String signature;
	private String signature2;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature2() {
		return signature2;
	}

	public void setSignature2(String signature2) {
		this.signature2 = signature2;
	}
}
