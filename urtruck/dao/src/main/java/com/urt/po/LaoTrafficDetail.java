package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoTrafficDetail implements Serializable {
    private Long batchId;

    private Long channelCustId;

    private Long userId;

    private String iccid;

    private String msisdn;

    private Integer operatorsId;

    private BigDecimal useCount;

    private String dataCycle;

    private Date recvTime;

    private String remark;

    private BigDecimal dataRemaining;

    private static final long serialVersionUID = 1L;

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
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getDataCycle() {
        return dataCycle;
    }

    public void setDataCycle(String dataCycle) {
        this.dataCycle = dataCycle == null ? null : dataCycle.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

	public BigDecimal getUseCount() {
		return useCount;
	}

	public void setUseCount(BigDecimal useCount) {
		this.useCount = useCount;
	}

	public BigDecimal getDataRemaining() {
		return dataRemaining;
	}

	public void setDataRemaining(BigDecimal dataRemaining) {
		this.dataRemaining = dataRemaining;
	}

}