package com.urt.dto.traffic;

import java.io.Serializable;

public class Totalinfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourcesCount ;
	private String usedresCount ;
	private String exceedusedCount ;
	private String ieavingsCount ;
	private String packageCode ;
	private String packageName ;
	private String beginTime ;
	private String endTime ;
	
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
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
    
}
