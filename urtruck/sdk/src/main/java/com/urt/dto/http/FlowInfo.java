package com.urt.dto.http;

import java.io.Serializable;

public class FlowInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 使用日期 */
	private String datePoint;
	/** 流量(KB) */
	private String flowSize;
	public String getDatePoint() {
		return datePoint;
	}
	public void setDatePoint(String datePoint) {
		this.datePoint = datePoint;
	}
	public String getFlowSize() {
		return flowSize;
	}
	public void setFlowSize(String flowSize) {
		this.flowSize = flowSize;
	}


}
