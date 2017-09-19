package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoSmsInfoDto implements Serializable {
    private Long smsId;

    private Long channelCustId;

    private Long userId;

    private String iccid;

    private String msisdn;

    private String smsContext;

    private Date sendTime;

    private Integer templeteId;

    private String operId;

    private String dealTag;

    private String resultInfo;

    private String errorCode;

    private String smsCorp;

    private Date updateTime;

    private String remark;

    private String outSmsid;

    private static final long serialVersionUID = 1L;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
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

    public String getSmsContext() {
        return smsContext;
    }

    public void setSmsContext(String smsContext) {
        this.smsContext = smsContext == null ? null : smsContext.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getTempleteId() {
        return templeteId;
    }

    public void setTempleteId(Integer templeteId) {
        this.templeteId = templeteId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getDealTag() {
        return dealTag;
    }

    public void setDealTag(String dealTag) {
        this.dealTag = dealTag == null ? null : dealTag.trim();
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo == null ? null : resultInfo.trim();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getSmsCorp() {
        return smsCorp;
    }

    public void setSmsCorp(String smsCorp) {
        this.smsCorp = smsCorp == null ? null : smsCorp.trim();
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

    public String getOutSmsid() {
        return outSmsid;
    }

    public void setOutSmsid(String outSmsid) {
        this.outSmsid = outSmsid == null ? null : outSmsid.trim();
    }
}