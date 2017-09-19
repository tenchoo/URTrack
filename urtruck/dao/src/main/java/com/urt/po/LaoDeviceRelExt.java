package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDeviceRelExt implements Serializable {
    private Long relId;

    private Long userId;

    private String iccid;

    private String idType;

    private String deviceId;

    private String relType;

    private String relAccount;

    private String validTag;

    private Date recvTime;

    private String operId;

    private Date updateTime;

    private String remark;
    
    private String relLogingname;
    
    private Long channelCustId;
    
    private String custName;

    private static final long serialVersionUID = 1L;

    
    public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType == null ? null : relType.trim();
    }

    public String getRelAccount() {
        return relAccount;
    }

    public void setRelAccount(String relAccount) {
        this.relAccount = relAccount == null ? null : relAccount.trim();
    }

    public String getValidTag() {
        return validTag;
    }

    public void setValidTag(String validTag) {
        this.validTag = validTag == null ? null : validTag.trim();
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
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
    public String getRelLogingname() {
        return relLogingname;
    }

    public void setRelLogingname(String relLogingname) {
        this.relLogingname = relLogingname == null ? null : relLogingname.trim();
    }
	public Long getChannelCustId() {
		return channelCustId;
	}

	public void setChannelCustId(Long channelCustId) {
		this.channelCustId = channelCustId;
	}
    
}