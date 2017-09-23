package com.urt.Exc;

public class BusiExceptionLog extends RuntimeException {
	
	private static final long serialVersionUID = 3583566093089790852L;
	private ExceCode msgCode;
	
	public BusiExceptionLog() {
		super();
	}

	public BusiExceptionLog(String message,ExceCode msgCode) {
		super(message);		
		this.msgCode=msgCode;
	}

	public BusiExceptionLog(Throwable cause) {
		super(cause);
	}

	public BusiExceptionLog(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceCode getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(ExceCode msgCode) {
		this.msgCode = msgCode;
	}
}
