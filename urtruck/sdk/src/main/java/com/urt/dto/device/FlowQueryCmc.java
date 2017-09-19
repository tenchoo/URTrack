package com.urt.dto.device;

import java.io.Serializable;
import java.util.List;

public class FlowQueryCmc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String respCode;
	public String respDesc;
	public String msidn;
	public List<CMCFlow> flowInfo;
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getMsidn() {
		return msidn;
	}
	public void setMsidn(String msidn) {
		this.msidn = msidn;
	}
	public List<CMCFlow> getFlowInfo() {
		return flowInfo;
	}
	public void setFlowInfo(List<CMCFlow> flowInfo) {
		this.flowInfo = flowInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
