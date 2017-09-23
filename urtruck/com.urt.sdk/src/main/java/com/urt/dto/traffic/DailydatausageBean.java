package com.urt.dto.traffic;

import java.io.Serializable;
import java.util.List;

public class DailydatausageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resultCode ;
	private String msisdn ;
	private String errorMessage ;
	private String mobileNo ;
	private List<OlMberFlows> olMberDdFlows ;
	private List<OlMberFlows> olMberMmFlows ;
	private List<OlMberFlows> totalinfoList ;
	private List<OlMberFlows> PackageVos ;
	
	
	public List<OlMberFlows> getPackageVos() {
		return PackageVos;
	}
	public void setPackageVos(List<OlMberFlows> packageVos) {
		PackageVos = packageVos;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public List<OlMberFlows> getOlMberDdFlows() {
		return olMberDdFlows;
	}
	public void setOlMberDdFlows(List<OlMberFlows> olMberDdFlows) {
		this.olMberDdFlows = olMberDdFlows;
	}
	public List<OlMberFlows> getOlMberMmFlows() {
		return olMberMmFlows;
	}
	public void setOlMberMmFlows(List<OlMberFlows> olMberMmFlows) {
		this.olMberMmFlows = olMberMmFlows;
	}
	public List<OlMberFlows> getTotalinfoList() {
		return totalinfoList;
	}
	public void setTotalinfoList(List<OlMberFlows> totalinfoList) {
		this.totalinfoList = totalinfoList;
	}
	
}
