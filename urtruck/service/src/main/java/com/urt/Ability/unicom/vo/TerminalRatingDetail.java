package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class TerminalRatingDetail implements Serializable {

	private static final long serialVersionUID = 810186222735993694L;
	/**	资费计划名称	*/
	private String ratePlanName;
	/**	队列位置，一直从0开始有效	*/
	private String queuePosition;
	/**	当前资费计费有效期	*/
	private String expirationDate;
	/**	资费计划时长	*/
	private String termLength;
	/**	资费计划剩余流量	*/
	private String dataRemaining;

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public String getQueuePosition() {
		return queuePosition;
	}

	public void setQueuePosition(String queuePosition) {
		this.queuePosition = queuePosition;
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

	public void setTermLength(String termLength) {
		this.termLength = termLength;
	}

	public String getDataRemaining() {
		return dataRemaining;
	}

	public void setDataRemaining(String dataRemaining) {
		this.dataRemaining = dataRemaining;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
