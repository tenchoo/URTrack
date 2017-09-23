package com.urt.dto.esim;

import java.io.Serializable;

public class ChangeOperDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isChange;
	private boolean isSuccess;
	private String operState;
	
	
	public boolean isChange() {
		return isChange;
	}
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getOperState() {
		return operState;
	}
	public void setOperState(String operState) {
		this.operState = operState;
	}
	
}
