package com.urt.Ability.unicom.jsonbean;

public class UnicomNotifyChangeBody {
	private String iccid;
	private String bodyName;
	private String ratePlanName;
	private String totalIncludedUsage;
	private String totalActualUsage;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getBodyName() {
		return bodyName;
	}

	public void setBodyName(String bodyName) {
		this.bodyName = bodyName;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public String getTotalIncludedUsage() {
		return totalIncludedUsage;
	}

	public void setTotalIncludedUsage(String totalIncludedUsage) {
		this.totalIncludedUsage = totalIncludedUsage;
	}

	public String getTotalActualUsage() {
		return totalActualUsage;
	}

	public void setTotalActualUsage(String totalActualUsage) {
		this.totalActualUsage = totalActualUsage;
	}
}
