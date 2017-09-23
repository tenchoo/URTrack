package com.urt.dto.chargeOff;

import java.io.Serializable;
import java.util.Date;

public class LaoOperatorsBillDto implements Serializable {
    private Long operatorsBillId;

    private Short partitionId;

    private Integer cycleId;

    private String outBillId;

    private Integer operatorsId;
    
    private String operatorsName;

    private Long channelCustId;

    private Long userId;

    private String iccid;

    private String msisdn;

    private String operatorsPid;

    private Integer planId;

    private String productName;

    private String useCount;

    private Long fee;

    private Long realFee;

    private String balanceTag;

    private Long glaFee;

    private String glaResult;

    private Date recvTime;

    private Date updateTime;

    private String updateAccountId;

    private String rsrvInfo1;

    private String rsrvInfo2;

    private static final long serialVersionUID = 1L;

    public Long getOperatorsBillId() {
        return operatorsBillId;
    }

    public void setOperatorsBillId(Long operatorsBillId) {
        this.operatorsBillId = operatorsBillId;
    }

    public Short getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Short partitionId) {
        this.partitionId = partitionId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public String getOutBillId() {
        return outBillId;
    }

    public void setOutBillId(String outBillId) {
        this.outBillId = outBillId == null ? null : outBillId.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
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

    public String getOperatorsPid() {
        return operatorsPid;
    }

    public void setOperatorsPid(String operatorsPid) {
        this.operatorsPid = operatorsPid == null ? null : operatorsPid.trim();
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount == null ? null : useCount.trim();
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getRealFee() {
        return realFee;
    }

    public void setRealFee(Long realFee) {
        this.realFee = realFee;
    }

    public String getBalanceTag() {
        return balanceTag;
    }

    public void setBalanceTag(String balanceTag) {
        this.balanceTag = balanceTag == null ? null : balanceTag.trim();
    }

    public Long getGlaFee() {
        return glaFee;
    }

    public void setGlaFee(Long glaFee) {
        this.glaFee = glaFee;
    }

    public String getGlaResult() {
        return glaResult;
    }

    public void setGlaResult(String glaResult) {
        this.glaResult = glaResult == null ? null : glaResult.trim();
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

    public String getUpdateAccountId() {
        return updateAccountId;
    }

    public void setUpdateAccountId(String updateAccountId) {
        this.updateAccountId = updateAccountId == null ? null : updateAccountId.trim();
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

	public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}
    
}
