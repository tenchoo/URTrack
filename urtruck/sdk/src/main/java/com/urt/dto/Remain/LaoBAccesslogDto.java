package com.urt.dto.Remain;

import java.io.Serializable;
import java.util.Date;

public class LaoBAccesslogDto implements Serializable {
    private Long accessId;

    private Long chargeId;

    private Long acctBalanceId;

    private Long oldBalance;

    private Long money;

    private Long newBalance;

    private Long invoiceFee;

    private String accessTag;

    private String recvOperId;

    private Date updateTime;

    private String cancelTag;

    private Date cancelTime;

    private String cancelOperId;
    
    private Long channelCustId;
    
    private Date createDate;
    
    private Short checkedTag;
    
    public Long getChannelCustId() {
		return channelCustId;
	}

	public void setChannelCustId(Long channelCustId) {
		this.channelCustId = channelCustId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getCheckedTag() {
		return checkedTag;
	}

	public void setCheckedTag(Short checkedTag) {
		this.checkedTag = checkedTag;
	}


    private static final long serialVersionUID = 1L;

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Long getAcctBalanceId() {
        return acctBalanceId;
    }

    public void setAcctBalanceId(Long acctBalanceId) {
        this.acctBalanceId = acctBalanceId;
    }

    public Long getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(Long oldBalance) {
        this.oldBalance = oldBalance;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Long newBalance) {
        this.newBalance = newBalance;
    }

    public Long getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(Long invoiceFee) {
        this.invoiceFee = invoiceFee;
    }

    public String getAccessTag() {
        return accessTag;
    }

    public void setAccessTag(String accessTag) {
        this.accessTag = accessTag == null ? null : accessTag.trim();
    }

    public String getRecvOperId() {
        return recvOperId;
    }

    public void setRecvOperId(String recvOperId) {
        this.recvOperId = recvOperId == null ? null : recvOperId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCancelTag() {
        return cancelTag;
    }

    public void setCancelTag(String cancelTag) {
        this.cancelTag = cancelTag == null ? null : cancelTag.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelOperId() {
        return cancelOperId;
    }

    public void setCancelOperId(String cancelOperId) {
        this.cancelOperId = cancelOperId == null ? null : cancelOperId.trim();
    }
}