package com.urt.web.dmp.entity;

import java.io.Serializable;
public class PostJsonData implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	
	 //数据内容
	private String posDataText;
    //数据md5加密文本
	private String encryptedText = "";
    //随机字符串文本
	private String randomText = "";
	
	private String custId="";

    /**设备类型字段 表示操作系统
     * android  0
     * windows  1
     */
	private byte deviceType = 0;

	
	
	public String getPosDataText() {
		return posDataText;
	}

	public void setPosDataText(String posDataText) {
		this.posDataText = posDataText;
	}

	public String getEncryptedText() {
		return encryptedText;
	}

	public void setEncryptedText(String encryptedText) {
		this.encryptedText = encryptedText;
	}

	public String getRandomText() {
		return randomText;
	}

	public void setRandomText(String randomText) {
		this.randomText = randomText;
	}

	public byte getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(byte deviceType) {
		this.deviceType = deviceType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "PostJsonData [posDataText=" + posDataText + ", encryptedText="
				+ encryptedText + ", randomText=" + randomText + ", custId="
				+ custId + ", deviceType=" + deviceType + "]";
	}
}
