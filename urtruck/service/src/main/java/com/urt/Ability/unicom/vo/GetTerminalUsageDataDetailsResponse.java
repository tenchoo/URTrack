package com.urt.Ability.unicom.vo;

import java.io.Serializable;
import java.util.List;

public class GetTerminalUsageDataDetailsResponse extends WsResponse implements
		Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6472558625297175171L;
	private String correlationId;
	private String version;
	private String build;
	private String timestamp;
	private String totalPages;
	private List<UsageDetail> list;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public List<UsageDetail> getList() {
		return list;
	}

	public void setList(List<UsageDetail> list) {
		this.list = list;
	}


	@Override
	public String toString() {
		return "GetTerminalUsageDataDetailsResponse [correlationId="
				+ correlationId + ", version=" + version + ", build=" + build
				+ ", timestamp=" + timestamp + ", totalPages=" + totalPages
				+ ", list=" + list + "]";
	}

}
