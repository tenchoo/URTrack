package com.urt.dto;

import java.io.Serializable;

/**
 * 类说明：密码实体
 * @author zhanbt3
 * @date 2016年6月24日  下午6:12:23
 */
public class PasswordBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String oldPassword;
	private String newPasswrod;
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPasswrod() {
		return newPasswrod;
	}
	
	public void setNewPasswrod(String newPasswrod) {
		this.newPasswrod = newPasswrod;
	}
	
	
	
}
