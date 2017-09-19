package com.urt.dto.chargeOff;

import java.io.Serializable;
import java.util.Date;

public class LaoUserbillsDetailDto implements Serializable {
    private Long billDetailId;

    private Long chargeId;

    private Integer cycleId;

    private Long channelCustId;

    private Long custId;

    private Long userId;

    private Long tradeId;

    private Long goodsId;

    private Long goodsReleaseId;

    private Integer operatosId;

    private Integer planId;

    private Integer itemId;

    private Long fee;

    private Long oweFee;

    private Long leftBillFee;

    private Date recvTime;

    private Date updateTime;

    private String operId;

    private String cancelTag;

    private String cancelOperId;

    private Date cancelTime;

    private String rsrvInfo1;

    private String rsrvInfo2;
    
    //下面的额外添加的
    
    private String iccid;
    
    private String msisdn;
    
    private String operatosName;
    
    private String goodsName;
    
    private String custName;
    
    private String status;

    private static final long serialVersionUID = 1L;

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
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

    public Integer getOperatosId() {
        return operatosId;
    }

    public void setOperatosId(Integer operatosId) {
        this.operatosId = operatosId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getOweFee() {
        return oweFee;
    }

    public void setOweFee(Long oweFee) {
        this.oweFee = oweFee;
    }

    public Long getLeftBillFee() {
        return leftBillFee;
    }

    public void setLeftBillFee(Long leftBillFee) {
        this.leftBillFee = leftBillFee;
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

	public String getOperatosName() {
		return operatosName;
	}

	public void setOperatosName(String operatosName) {
		this.operatosName = operatosName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}