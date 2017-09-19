package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 流量累加月表
 * @author liuxl
 *
 */
public class LaoTrafficMm implements Serializable {
	
	//流水号
    private Long batchId;

    //渠道客户ID
    private Long channelCustId;

    //用户ID
    private Long userId;

    //ICCID
    private String iccid;

    //用户号码
    private String msisdn;

    //运营商
    private Integer operatorsId;

    //累加使用量，KB
    private BigDecimal useCount;

    //数据周期，如一月流量，则为201611
    private String dataCycle;

    //累加的流量最新时间YYYYMMDD，如20161114
    private String dataAdded;

    //入表时间
    private Date recvTime;

    //更新时间
    private Date updateTime;

    //备注
    private String remark;

    //剩余流量，KB
    private BigDecimal dataRemaining;
    
    //基础套餐
    private String planClassify;

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

    public String getDataAdded() {
        return dataAdded;
    }

    public void setDataAdded(String dataAdded) {
        this.dataAdded = dataAdded == null ? null : dataAdded.trim();
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

	public String getPlanClassify() {
		return planClassify;
	}

	public void setPlanClassify(String planClassify) {
		this.planClassify = planClassify;
	}

	
}