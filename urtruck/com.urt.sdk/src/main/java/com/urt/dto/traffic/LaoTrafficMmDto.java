package com.urt.dto.traffic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoTrafficMmDto implements Serializable {

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

    private BigDecimal useCount;

    private String dataCycle;

    private String dataAdded;

    private Date recvTime;

    private Date updateTime;

    private String remark;

    private BigDecimal dataRemaining;
    
    private String staticNameA;

    private String staticNameB;
    
    private Date activeDate;
    
    private Date endDate;
    
    private String cardStatus;
    
    private String imsi;
    
    private String type;
    
    private String imei;
    
    private String planClassify;
    
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStaticNameA() {
		return staticNameA;
	}

	public void setStaticNameA(String staticNameA) {
		this.staticNameA = staticNameA;
	}

	public String getStaticNameB() {
		return staticNameB;
	}

	public void setStaticNameB(String staticNameB) {
		this.staticNameB = staticNameB;
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
  
   
    
	public String getPlanClassify() {
		return planClassify;
	}

	public void setPlanClassify(String planClassify) {
		this.planClassify = planClassify;
	}

	@Override
	public String toString() {
		return "LaoTrafficMmDto [batchId=" + batchId + ", channelCustId="
				+ channelCustId + ", userId=" + userId + ", iccid=" + iccid
				+ ", msisdn=" + msisdn + ", operatorsId=" + operatorsId
				+ ", useCount=" + useCount + ", dataCycle=" + dataCycle
				+ ", dataAdded=" + dataAdded + ", recvTime=" + recvTime
				+ ", updateTime=" + updateTime + ", remark=" + remark
				+ ", dataRemaining=" + dataRemaining + ", staticNameA="
				+ staticNameA + ", staticNameB=" + staticNameB
				+ ", activeDate=" + activeDate + ", endDate=" + endDate
				+ ", cardStatus=" + cardStatus + ", imsi=" + imsi + ", type="
				+ type + ", imei=" + imei +",planClassify="+planClassify+ "]";
	}
    
}