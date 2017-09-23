package com.urt.dto.traffic;

import java.io.Serializable;

public class PackagePlanDto implements Serializable {

	private static final long serialVersionUID = 810186222735993694L;


	/** 套餐计划名称 */
	private String packageName;
	/** 套餐计划总量 */
	private String packageCount;
	/** 套餐计划剩余量 */
	private String packageRemain;
	/** 套餐计划生效时间 */
	private String packageStartTime;
	/** 套餐计划截止时间 */
	private String packageEndTime;
	/** 套餐计划有效天数*/
	private String packageTimeLenth;
	/** 异常信息 */
	private String expMsg;
	
	
	public String getPackageTimeLenth() {
		return packageTimeLenth;
	}
	public void setPackageTimeLenth(String packageTimeLenth) {
		this.packageTimeLenth = packageTimeLenth;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageCount() {
		return packageCount;
	}
	public void setPackageCount(String packageCount) {
		this.packageCount = packageCount;
	}
	public String getPackageRemain() {
		return packageRemain;
	}
	public void setPackageRemain(String packageRemain) {
		this.packageRemain = packageRemain;
	}
	public String getPackageStartTime() {
		return packageStartTime;
	}
	public void setPackageStartTime(String packageStartTime) {
		this.packageStartTime = packageStartTime;
	}
	public String getPackageEndTime() {
		return packageEndTime;
	}
	public void setPackageEndTime(String packageEndTime) {
		this.packageEndTime = packageEndTime;
	}
	public String getExpMsg() {
		return expMsg;
	}
	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}
}
