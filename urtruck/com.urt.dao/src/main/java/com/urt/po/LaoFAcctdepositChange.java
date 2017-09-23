package com.urt.po;

import java.io.Serializable;
import java.util.Date;
/**
 * 账本变化po
 * @author admin
 *
 */
public class LaoFAcctdepositChange implements Serializable {
    private Long accountChangeId;    //主键ID

    private Long acctBalanceId;      //账本ID

    private Long channelCustId;      //渠道企业ID

    private Date createDate;         //创建时间

    private Date updateDate;         //更新时间

    private Long updateMoney;        //更新的money

    private Long oldMoney;           //本次更新前的账本余额

    private Long newMoney;           //更新的总钱数

    private Short changeType;        //账本变化类型  0 充值  1 消费  2 返现  3 提现

    private Long moveId;             //提现流水

    private Long accessId;           //充值流水

    private Long chargeId;           //消费流水

    private Long balanceId;          //返现流水

    private String operId;           //操作人员ID

    private String rsrvStr1;         //备用字段1

    private String rsrvStr2;         //备用字段2

    private static final long serialVersionUID = 1L;

    public Long getAccountChangeId() {
        return accountChangeId;
    }

    public void setAccountChangeId(Long accountChangeId) {
        this.accountChangeId = accountChangeId;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateMoney() {
        return updateMoney;
    }

    public void setUpdateMoney(Long updateMoney) {
        this.updateMoney = updateMoney;
    }

    public Long getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(Long oldMoney) {
        this.oldMoney = oldMoney;
    }

    public Long getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(Long newMoney) {
        this.newMoney = newMoney;
    }

    public Short getChangeType() {
        return changeType;
    }

    public void setChangeType(Short changeType) {
        this.changeType = changeType;
    }

    public Long getMoveId() {
        return moveId;
    }

    public void setMoveId(Long moveId) {
        this.moveId = moveId;
    }

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

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
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