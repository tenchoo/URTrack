package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class SmsMessage implements Serializable {

	private static final long serialVersionUID = 22364638247352987L;

	private String smsMsgId;
	private String status;
	private String messageText;
	private String senderLogin;
	private String sentToIccid;
	private String sentFrom;
	private String smsMsgAttemptStatus;
	private String msgType;
	private String dateSent;
	private String dateReceived;
	private String dateAdded;
	private String dateModified;

	public String getSmsMsgId() {
		return smsMsgId;
	}

	public void setSmsMsgId(String smsMsgId) {
		this.smsMsgId = smsMsgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSenderLogin() {
		return senderLogin;
	}

	public void setSenderLogin(String senderLogin) {
		this.senderLogin = senderLogin;
	}

	public String getSentToIccid() {
		return sentToIccid;
	}

	public void setSentToIccid(String sentToIccid) {
		this.sentToIccid = sentToIccid;
	}

	public String getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}

	public String getSmsMsgAttemptStatus() {
		return smsMsgAttemptStatus;
	}

	public void setSmsMsgAttemptStatus(String smsMsgAttemptStatus) {
		this.smsMsgAttemptStatus = smsMsgAttemptStatus;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getDateSent() {
		return dateSent;
	}

	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

}
