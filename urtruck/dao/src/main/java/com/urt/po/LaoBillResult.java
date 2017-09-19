package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoBillResult implements Serializable {
    private Long balanceId;

    private Long chargeId;

    private Long channelCustId;

    private Integer cycleId;

    private Long backFee;

    private Date balanceTime;

    private Long leftFee;

    private String canpayTag;

    private String cashbackTag;

    private Date cashTime;

    private Long cashChargeId;

    private String recvOperId;

    private Long ruleId;

    private String cancelTag;

    private String cancelOperId;

    private Date cancelTime;

    private Long cancelChargeId;

    private String rsrvInfo1;

    private String rsrvInfo2;

    private static final long serialVersionUID = 1L;

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public Long getBackFee() {
        return backFee;
    }

    public void setBackFee(Long backFee) {
        this.backFee = backFee;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Long getLeftFee() {
        return leftFee;
    }

    public void setLeftFee(Long leftFee) {
        this.leftFee = leftFee;
    }

    public String getCanpayTag() {
        return canpayTag;
    }

    public void setCanpayTag(String canpayTag) {
        this.canpayTag = canpayTag == null ? null : canpayTag.trim();
    }

    public String getCashbackTag() {
        return cashbackTag;
    }

    public void setCashbackTag(String cashbackTag) {
        this.cashbackTag = cashbackTag == null ? null : cashbackTag.trim();
    }

    public Date getCashTime() {
        return cashTime;
    }

    public void setCashTime(Date cashTime) {
        this.cashTime = cashTime;
    }

    public Long getCashChargeId() {
        return cashChargeId;
    }

    public void setCashChargeId(Long cashChargeId) {
        this.cashChargeId = cashChargeId;
    }

    public String getRecvOperId() {
        return recvOperId;
    }

    public void setRecvOperId(String recvOperId) {
        this.recvOperId = recvOperId == null ? null : recvOperId.trim();
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getCancelTag() {
        return cancelTag;
    }

    public void setCancelTag(String cancelTag) {
        this.cancelTag = cancelTag == null ? null : cancelTag.trim();
    }

    public String getCancelOperId() {
        return cancelOperId;
    }

    public void setCancelOperId(String cancelOperId) {
        this.cancelOperId = cancelOperId == null ? null : cancelOperId.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getCancelChargeId() {
        return cancelChargeId;
    }

    public void setCancelChargeId(Long cancelChargeId) {
        this.cancelChargeId = cancelChargeId;
    }

    public String getRsrvInfo1() {
        return rsrvInfo1;
    }

    public void setRsrvInfo1(String rsrvInfo1) {
        this.rsrvInfo1 = rsrvInfo1 == null ? null : rsrvInfo1.trim();
    }

    public String getRsrvInfo2() {
        return rsrvInfo2;
    }

    public void setRsrvInfo2(String rsrvInfo2) {
        this.rsrvInfo2 = rsrvInfo2 == null ? null : rsrvInfo2.trim();
    }
}