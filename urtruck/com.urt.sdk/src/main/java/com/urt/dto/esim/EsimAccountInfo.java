package com.urt.dto.esim;

import java.io.Serializable;
import java.util.List;

public class EsimAccountInfo implements Serializable{
	
	/**
	 * 账户流量信息
	 */
	private static final long serialVersionUID = 1L;
	private String retcode;//错误编码(-2检验失败 -4系统异常 1成功)
	private String lenovoId;
	private String curentDate;
	private List<EsimFlowInfo> infoFlow; //账户剩余流量 --中国 西班牙
	private List<EsimDeviceInfo> imeiFlowInfo; //设备流量信息
	private List<EsimInfo>  imeiInfo; //设备信息
	
	
	public String getCurentDate() {
		return curentDate;
	}
	public void setCurentDate(String curentDate) {
		this.curentDate = curentDate;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getLenovoId() {
		return lenovoId;
	}
	public void setLenovoId(String lenovoId) {
		this.lenovoId = lenovoId;
	}
	public List<EsimFlowInfo> getInfoFlow() {
		return infoFlow;
	}
	public void setInfoFlow(List<EsimFlowInfo> infoFlow) {
		this.infoFlow = infoFlow;
	}
	public List<EsimDeviceInfo> getImeiFlowInfo() {
		return imeiFlowInfo;
	}
	public void setImeiFlowInfo(List<EsimDeviceInfo> imeiFlowInfo) {
		this.imeiFlowInfo = imeiFlowInfo;
	}
	public List<EsimInfo> getImeiInfo() {
		return imeiInfo;
	}
	public void setImeiInfo(List<EsimInfo> imeiInfo) {
		this.imeiInfo = imeiInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
