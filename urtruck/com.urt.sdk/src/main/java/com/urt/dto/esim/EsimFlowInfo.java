package com.urt.dto.esim;

import java.io.Serializable;

public class EsimFlowInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operator;
	private String surplusFlow;
	private String goodsName;
	private String totalFlow;
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSurplusFlow() {
		return surplusFlow;
	}
	public void setSurplusFlow(String surplusFlow) {
		this.surplusFlow = surplusFlow;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTotalFlow() {
		return totalFlow;
	}
	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
