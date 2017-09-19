package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoFAcctDepositDto implements Serializable {
    private Long acctBalanceId;

    private Long channelCustId;

    private Long initMoney;

    private Long depositMoney;

    private Long invoiceFee;

    private Long printFee;

    private Long startCycId;

    private Long endCycId;

    private Date startDate;

    private Date endDate;

    private String cashTag;

    private String updateStaffId;

    private Date updateTime;

    private String operId;

    private String remark;

    private String rsrvStr1;

    private String rsrvStr2;

    private static final long serialVersionUID = 1L;

    public Long getAcctBalanceId() {
        return acctBalanceId;
    }

    public void setAcctBalanceId(Long acctBalanceId) {
        this.acctBalanceId = acctBalanceId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Long getInitMoney() {
        return initMoney;
    }

    public void setInitMoney(Long initMoney) {
        this.initMoney = initMoney;
    }

    public Long getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(Long depositMoney) {
        this.depositMoney = depositMoney;
    }

    public Long getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(Long invoiceFee) {
        this.invoiceFee = invoiceFee;
    }

    public Long getPrintFee() {
        return printFee;
    }

    public void setPrintFee(Long printFee) {
        this.printFee = printFee;
    }

    public Long getStartCycId() {
        return startCycId;
    }

    public void setStartCycId(Long startCycId) {
        this.startCycId = startCycId;
    }

    public Long getEndCycId() {
        return endCycId;
    }

    public void setEndCycId(Long endCycId) {
        this.endCycId = endCycId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCashTag() {
        return cashTag;
    }

    public void setCashTag(String cashTag) {
        this.cashTag = cashTag == null ? null : cashTag.trim();
    }

    public String getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(String updateStaffId) {
        this.updateStaffId = updateStaffId == null ? null : updateStaffId.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1 == null ? null : rsrvStr1.trim();
    }

    public String getRsrvStr2() {
        return rsrvStr2;
    }

    public void setRsrvStr2(String rsrvStr2) {
        this.rsrvStr2 = rsrvStr2 == null ? null : rsrvStr2.trim();
    }
}