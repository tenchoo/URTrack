package com.urt.dto.traffic;

import java.io.Serializable;

public class OlMberFlows implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String flowSum ;
	private String mmflowNum ;
	private String ddFlowNum2G3G ;
	private String mmFlowNum2G3G ;
	private String ddFlowNum4G ;
	private String mmFlowNum4G ;
	private String dateStr ;
	private Totalinfo totalinfo ;
	private Totalinfo packageInfoVo ;
	
	
	public Totalinfo getPackageInfoVo() {
		return packageInfoVo;
	}
	public void setPackageInfoVo(Totalinfo packageInfoVo) {
		this.packageInfoVo = packageInfoVo;
	}
	public Totalinfo getTotalinfo() {
		return totalinfo;
	}
	public void setTotalinfo(Totalinfo totalinfo) {
		this.totalinfo = totalinfo;
	}
	public String getMmflowNum() {
		return mmflowNum;
	}
	public void setMmflowNum(String mmflowNum) {
		this.mmflowNum = mmflowNum;
	}
	public String getMmFlowNum2G3G() {
		return mmFlowNum2G3G;
	}
	public void setMmFlowNum2G3G(String mmFlowNum2G3G) {
		this.mmFlowNum2G3G = mmFlowNum2G3G;
	}
	public String getMmFlowNum4G() {
		return mmFlowNum4G;
	}
	public void setMmFlowNum4G(String mmFlowNum4G) {
		this.mmFlowNum4G = mmFlowNum4G;
	}
	public String getFlowSum() {
		return flowSum;
	}
	public void setFlowSum(String flowSum) {
		this.flowSum = flowSum;
	}
	public String getDdFlowNum2G3G() {
		return ddFlowNum2G3G;
	}
	public void setDdFlowNum2G3G(String ddFlowNum2G3G) {
		this.ddFlowNum2G3G = ddFlowNum2G3G;
	}
	public String getDdFlowNum4G() {
		return ddFlowNum4G;
	}
	public void setDdFlowNum4G(String ddFlowNum4G) {
		this.ddFlowNum4G = ddFlowNum4G;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
}
