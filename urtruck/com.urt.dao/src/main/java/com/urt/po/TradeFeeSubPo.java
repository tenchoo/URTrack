package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class TradeFeeSubPo implements Serializable {
    private Long tradeId;

    private Short acceptMonth;

    private Long goodsId;

    private String oldfee;

    private String fee;

    private Long discontId;

    private String derateRemark;

    private String calculateTag;

    private String payTag;

    private String payOrderId;

    private Date payDate;

    private String feeStaffId;

    private String payType;

    private Date acceptDate;
    
    //添加额外的属性
    private String goodsName;
    private String custName;
    private String orderResult;
    private String iccid;
    private String goodsPrices;

    private static final long serialVersionUID = 1L;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Short getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(Short acceptMonth) {
        this.acceptMonth = acceptMonth;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getOldfee() {
        return oldfee;
    }

    public void setOldfee(String oldfee) {
        this.oldfee = oldfee == null ? null : oldfee.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public Long getDiscontId() {
        return discontId;
    }

    public void setDiscontId(Long discontId) {
        this.discontId = discontId;
    }

    public String getDerateRemark() {
        return derateRemark;
    }

    public void setDerateRemark(String derateRemark) {
        this.derateRemark = derateRemark == null ? null : derateRemark.trim();
    }

    public String getCalculateTag() {
        return calculateTag;
    }

    public void setCalculateTag(String calculateTag) {
        this.calculateTag = calculateTag == null ? null : calculateTag.trim();
    }

    public String getPayTag() {
        return payTag;
    }

    public void setPayTag(String payTag) {
        this.payTag = payTag == null ? null : payTag.trim();
    }

    public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getFeeStaffId() {
        return feeStaffId;
    }

    public void setFeeStaffId(String feeStaffId) {
        this.feeStaffId = feeStaffId == null ? null : feeStaffId.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
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

	public String getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(String orderResult) {
		this.orderResult = orderResult;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getGoodsPrices() {
		return goodsPrices;
	}

	public void setGoodsPrices(String goodsPrices) {
		this.goodsPrices = goodsPrices;
	}
}