package com.urt.dto.Remain;

import java.io.Serializable;
import java.util.Date;

public class LaoBPaylogDto implements Serializable {
    private Long chargeId;

    private Long channelCustId;

    private Long custId;

    private Long userId;

    private Short payFeeModeCode;

    private Integer paymentOp;

    private Long tradeId;

    private Short tradeTypeCode;

    private String payTag;

    private Long recvFee;

    private Long fee;

    private Long discntFee;

    private Long realFee;

    private String resType;

    private Long resNum;

    private String userItem1;

    private String userItem2;

    private String userItem3;

    private String userItem4;

    private Long goodsId;

    private Date recvTime;

    private String saleManager;

    private String recvOperId;

    private String feecntTag;

    private Date feecntTime;

    private String remark;

    private String cancelTag;

    private String cancelOperId;

    private Date cancelTime;

    private String rsrvInfo1;

    private String rsrvInfo2;
    
    //前台展示使用
    private String custName;
    
    private String iccid;
    
    private String msisdn;
    
    private String operatorName;
    
    private String goodName;
    
    private String staticName;
    
    private String status;
    

    private static final long serialVersionUID = 1L;

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

    public Short getPayFeeModeCode() {
        return payFeeModeCode;
    }

    public void setPayFeeModeCode(Short payFeeModeCode) {
        this.payFeeModeCode = payFeeModeCode;
    }

    public Integer getPaymentOp() {
        return paymentOp;
    }

    public void setPaymentOp(Integer paymentOp) {
        this.paymentOp = paymentOp;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
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

    public Long getDiscntFee() {
        return discntFee;
    }

    public void setDiscntFee(Long discntFee) {
        this.discntFee = discntFee;
    }

    public Long getRealFee() {
        return realFee;
    }

    public void setRealFee(Long realFee) {
        this.realFee = realFee;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public Long getResNum() {
        return resNum;
    }

    public void setResNum(Long resNum) {
        this.resNum = resNum;
    }

    public String getUserItem1() {
        return userItem1;
    }

    public void setUserItem1(String userItem1) {
        this.userItem1 = userItem1 == null ? null : userItem1.trim();
    }

    public String getUserItem2() {
        return userItem2;
    }

    public void setUserItem2(String userItem2) {
        this.userItem2 = userItem2 == null ? null : userItem2.trim();
    }

    public String getUserItem3() {
        return userItem3;
    }

    public void setUserItem3(String userItem3) {
        this.userItem3 = userItem3 == null ? null : userItem3.trim();
    }

    public String getUserItem4() {
        return userItem4;
    }

    public void setUserItem4(String userItem4) {
        this.userItem4 = userItem4 == null ? null : userItem4.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager == null ? null : saleManager.trim();
    }

    public String getRecvOperId() {
        return recvOperId;
    }

    public void setRecvOperId(String recvOperId) {
        this.recvOperId = recvOperId == null ? null : recvOperId.trim();
    }

    public String getFeecntTag() {
        return feecntTag;
    }

    public void setFeecntTag(String feecntTag) {
        this.feecntTag = feecntTag == null ? null : feecntTag.trim();
    }

    public Date getFeecntTime() {
        return feecntTime;
    }

    public void setFeecntTime(Date feecntTime) {
        this.feecntTime = feecntTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getStaticName() {
		return staticName;
	}

	public void setStaticName(String staticName) {
		this.staticName = staticName;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
