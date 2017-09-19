package com.urt.po;

import java.io.Serializable;

public class BBillExtPo implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serialNumber;
	 
	private Integer pItemId;
	 
	private String pItemName;
	 
	private Long fee;
	 
	private Long bDiscnt;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getpItemId() {
		return pItemId;
	}

	public void setpItemId(Integer pItemId) {
		this.pItemId = pItemId;
	}


	public String getpItemName() {
		return pItemName;
	}

	public void setpItemName(String pItemName) {
		this.pItemName = pItemName;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getbDiscnt() {
		return bDiscnt;
	}

	public void setbDiscnt(Long bDiscnt) {
		this.bDiscnt = bDiscnt;
	}
	 
}
