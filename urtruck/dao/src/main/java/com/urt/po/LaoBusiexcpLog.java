package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoBusiexcpLog implements Serializable {
    private Long excpId;

    private Long tradeId;

    private Long batchId;

    private Long batchdetailId;

    private Long channelCustId;

    private Long custId;

    private Long userId;

    private String msisdn;

    private String iccid;

    private Long goodsId;

    private Short tradeTypeCode;

    private Short excpTypeCode;

    private String dealTag;

    private String resultInfo;

    private Date recvTime;

    private Date updateTime;

    private Short doneTimes;

    private String operId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getExcpId() {
        return excpId;
    }

    public void setExcpId(Long excpId) {
        this.excpId = excpId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getBatchdetailId() {
        return batchdetailId;
    }

    public void setBatchdetailId(Long batchdetailId) {
        this.batchdetailId = batchdetailId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public Short getExcpTypeCode() {
        return excpTypeCode;
    }

    public void setExcpTypeCode(Short excpTypeCode) {
        this.excpTypeCode = excpTypeCode;
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

    public Short getDoneTimes() {
        return doneTimes;
    }

    public void setDoneTimes(Short doneTimes) {
        this.doneTimes = doneTimes;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}