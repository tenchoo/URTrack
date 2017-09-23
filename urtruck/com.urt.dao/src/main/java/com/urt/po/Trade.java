package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class Trade implements Serializable {
    private Long tradeId;

    private Short acceptMonth;

    private Short tradeTypeCode;

    private String inModeCode;

    private String subscribeState;

    private Long goodsId;
    
    private Integer goodsReleaseId;

    private Long userId;

    private Long channalCustId;

    private Long custId;

    private String iccid;

    private String custName;

    private Date acceptDate;

    private Date execTime;

    private Date finishDate;

    private String fee;

    private String invoiceNo;

    private String feeState;

    private Date feeTime;

    private String feeStaffId;

    private String ifMaintenance;

    private Long orderId;

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

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getInModeCode() {
        return inModeCode;
    }

    public void setInModeCode(String inModeCode) {
        this.inModeCode = inModeCode == null ? null : inModeCode.trim();
    }

    public String getSubscribeState() {
        return subscribeState;
    }

    public void setSubscribeState(String subscribeState) {
        this.subscribeState = subscribeState == null ? null : subscribeState.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannalCustId() {
        return channalCustId;
    }

    public void setChannalCustId(Long channalCustId) {
        this.channalCustId = channalCustId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getFeeState() {
        return feeState;
    }

    public void setFeeState(String feeState) {
        this.feeState = feeState == null ? null : feeState.trim();
    }

    public Date getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }

    public String getFeeStaffId() {
        return feeStaffId;
    }

    public void setFeeStaffId(String feeStaffId) {
        this.feeStaffId = feeStaffId == null ? null : feeStaffId.trim();
    }

    public String getIfMaintenance() {
        return ifMaintenance;
    }

    public void setIfMaintenance(String ifMaintenance) {
        this.ifMaintenance = ifMaintenance == null ? null : ifMaintenance.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	public Integer getGoodsReleaseId() {
		return goodsReleaseId;
	}

	public void setGoodsReleaseId(Integer goodsReleaseId) {
		this.goodsReleaseId = goodsReleaseId;
	}

	
    
}