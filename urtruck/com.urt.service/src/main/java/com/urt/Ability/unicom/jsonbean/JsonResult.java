package com.urt.Ability.unicom.jsonbean;

import java.io.Serializable;

public class JsonResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -387187357871244683L;
	
	private Integer status;
	
	private String msg;
	
	private String toUrl;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
