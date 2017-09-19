package com.urt.dto.traffic;

import java.io.Serializable;
import java.util.Date;

public class LaoSimDateDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5007950771588073646L;
	
    private Long batchId;

    private Long channelCustId;

    private Long userId;

    private String iccid;

    private String msisdn;

    private Integer operatorsId;

    private String dayUseCount;// 日使用量
    
    private String monthUseCount;// 月使用量

    private String dataRemaining;// 剩余量
    
    private String monthCycle;//月账期
    
    private String data;// 日期
 
    private String state;// 卡状态
    
	private String imei;// 入网编号
	
	private String imsi;// 小卡卡号
	
    private Date recvTime;

    private String remark;
    
    private String dateActivated;// 激活时间
    
    private String renewalPolicy;// 续约模式
    
	private String monthSign; //月付灵活共享，月付单卡的标识

    
	public String getMonthSign() {
		return monthSign;
	}

	public void setMonthSign(String monthSign) {
		this.monthSign = monthSign;
	}

	public String getRenewalPolicy() {
		return renewalPolicy;
	}

	public void setRenewalPolicy(String renewalPolicy) {
		this.renewalPolicy = renewalPolicy;
	}

	public String getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getChannelCustId() {
		return channelCustId;
	}

	public void setChannelCustId(Long channelCustId) {
		this.channelCustId = channelCustId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getOperatorsId() {
		return operatorsId;
	}

	public void setOperatorsId(Integer operatorsId) {
		this.operatorsId = operatorsId;
	}

	public String getDayUseCount() {
		return dayUseCount;
	}

	public void setDayUseCount(String dayUseCount) {
		this.dayUseCount = dayUseCount;
	}

	public String getMonthUseCount() {
		return monthUseCount;
	}

	public void setMonthUseCount(String monthUseCount) {
		this.monthUseCount = monthUseCount;
	}

	public String getDataRemaining() {
		return dataRemaining;
	}

	public void setDataRemaining(String dataRemaining) {
		this.dataRemaining = dataRemaining;
	}

	public String getMonthCycle() {
		return monthCycle;
	}

	public void setMonthCycle(String monthCycle) {
		this.monthCycle = monthCycle;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

}