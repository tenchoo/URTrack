package com.urt.Ability.unicom.vo;

public class NowRatePlanResponse {
	/**	资费计划名称	*/
	private String ratePlanName;
	/**	当前资费计费有效期	*/
	private String expirationDate;
	/**	资费计划时长	*/
	private String termLength;
	/**	资费计划剩余流量	*/
	private String dataRemaining;
	//对应的本地库中的标识ID
	private Long userLinkedTariffPlanId;
	private String totalCapacithy;
	
	public String getRatePlanName() {
		return ratePlanName;
	}
	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getTermLength() {
		return termLength;
	}
	public String getTotalCapacithy() {
		return totalCapacithy;
	}
	public void setTotalCapacithy(String totalCapacithy) {
		this.totalCapacithy = totalCapacithy;
	}
	public void setTermLength(String termLength) {
		this.termLength = termLength;
	}
	public String getDataRemaining() {
		return dataRemaining;
	}
	public void setDataRemaining(String dataRemaining) {
		this.dataRemaining = dataRemaining;
	}
	public Long getUserLinkedTariffPlanId() {
		return userLinkedTariffPlanId;
	}
	public void setUserLinkedTariffPlanId(Long userLinkedTariffPlanId) {
		this.userLinkedTariffPlanId = userLinkedTariffPlanId;
	}
	
	

}
