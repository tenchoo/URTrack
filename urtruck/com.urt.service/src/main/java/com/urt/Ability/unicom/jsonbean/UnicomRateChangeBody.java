package com.urt.Ability.unicom.jsonbean;

public class UnicomRateChangeBody {
	
	private String iccid;
	private String oldRatePlanName;
	private String newRatePlanName;
	private String dataChanged;
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getOldRatePlanName() {
		return oldRatePlanName;
	}
	public void setOldRatePlanName(String oldRatePlanName) {
		this.oldRatePlanName = oldRatePlanName;
	}
	public String getNewRatePlanName() {
		return newRatePlanName;
	}
	public void setNewRatePlanName(String newRatePlanName) {
		this.newRatePlanName = newRatePlanName;
	}
	public String getDataChanged() {
		return dataChanged;
	}
	public void setDataChanged(String dataChanged) {
		this.dataChanged = dataChanged;
	}
	
}
