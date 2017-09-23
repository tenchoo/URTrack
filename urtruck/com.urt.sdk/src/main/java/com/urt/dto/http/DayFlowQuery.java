package com.urt.dto.http;

import java.io.Serializable;
import java.util.List;

public class DayFlowQuery  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String respDesc;
	private List<FlowInfo> flowInfo;

	public List<FlowInfo> getFlowInfo() {
		return flowInfo;
	}
	public void setFlowInfo(List<FlowInfo> flowInfo) {
		this.flowInfo = flowInfo;
	}
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
	



}
