package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class CardStatusQRRsp  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productStatusCd; 	//卡状态标识
	private String productStatusName;	//卡状态信息
	private String servCreateDate;		//创建服务时间
	private String number;				//设备号
	private Long result;
	private String resultMsg;
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public String getProductStatusCd() {
		return productStatusCd;
	}
	public void setProductStatusCd(String productStatusCd) {
		this.productStatusCd = productStatusCd;
	}
	public String getProductStatusName() {
		return productStatusName;
	}
	public void setProductStatusName(String productStatusName) {
		this.productStatusName = productStatusName;
	}
	public String getServCreateDate() {
		return servCreateDate;
	}
	public void setServCreateDate(String servCreateDate) {
		this.servCreateDate = servCreateDate;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	
}
