package com.urt.dto.device;

import java.io.Serializable;

public class CMCFlow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourcesCount;  //套餐总量
	private String usedresCount;   //已使用总量
	private String exceedusedCount; //超出套餐部分使用量
	private String ieavingsCount;  //剩余累计量
	public String getResourcesCount() {
		return resourcesCount;
	}
	public void setResourcesCount(String resourcesCount) {
		this.resourcesCount = resourcesCount;
	}
	public String getUsedresCount() {
		return usedresCount;
	}
	public void setUsedresCount(String usedresCount) {
		this.usedresCount = usedresCount;
	}
	public String getExceedusedCount() {
		return exceedusedCount;
	}
	public void setExceedusedCount(String exceedusedCount) {
		this.exceedusedCount = exceedusedCount;
	}
	public String getIeavingsCount() {
		return ieavingsCount;
	}
	public void setIeavingsCount(String ieavingsCount) {
		this.ieavingsCount = ieavingsCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
