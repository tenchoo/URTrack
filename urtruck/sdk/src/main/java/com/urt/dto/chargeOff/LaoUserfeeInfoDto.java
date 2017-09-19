package com.urt.dto.chargeOff;

import java.io.Serializable;
import java.util.Date;

public class LaoUserfeeInfoDto implements Serializable {
	private Long chargeId;

    private Integer acceptMonth;

    private Long channelCustId;

    private Long custId;

    private Long userId;

    private String msisdn;

    private String iccid;

    private Long tradeId;

    private Long goodsId;

    private Long goodsReleaseId;

    private Integer operatorsId;

    private String payTag;

    private Long recvFee;

    private Long fee;

    private Long billFee;

    private Integer startBillCyc;

    private Integer endBillCyc;

    private Integer lastestBillCyc;

    private Date recvTime;

    private Date updateTime;

    private String operId;

    private String cancelTag;

    private String cancelOperId;

    private Date cancelTime;

    private String rsrvInfo1;

    private String rsrvInfo2;

    private static final long serialVersionUID = 1L;

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(Integer acceptMonth) {
        this.acceptMonth = acceptMonth;
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

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsReleaseId() {
        return goodsReleaseId;
    }

    public void setGoodsReleaseId(Long goodsReleaseId) {
        this.goodsReleaseId = goodsReleaseId;
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getPayTag() {
        return payTag;
    }

    public void setPayTag(String payTag) {
        this.payTag = payTag == null ? null : payTag.trim();
    }

    public Long getRecvFee() {
        return recvFee;
    }

    public void setRecvFee(Long recvFee) {
        this.recvFee = recvFee;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getBillFee() {
        return billFee;
    }

    public void setBillFee(Long billFee) {
        this.billFee = billFee;
    }

    public Integer getStartBillCyc() {
        return startBillCyc;
    }

    public void setStartBillCyc(Integer startBillCyc) {
        this.startBillCyc = startBillCyc;
    }

    public Integer getEndBillCyc() {
        return endBillCyc;
    }

    public void setEndBillCyc(Integer endBillCyc) {
        this.endBillCyc = endBillCyc;
    }

    public Integer getLastestBillCyc() {
        return lastestBillCyc;
    }

    public void setLastestBillCyc(Integer lastestBillCyc) {
        this.lastestBillCyc = lastestBillCyc;
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

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
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