package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class BBillExt implements Serializable {
    private Long billId;

    private Short partitionId;

    private Long acctId;

    private Long userId;

    private String serialNumber;

    private Integer itemId;

    private Integer cycleId;

    private Long useCount;

    private Long fee;

    private Long balance;

    private Long bDiscnt;

    private Long aDiscnt;

    private Long adjustBefore;

    private Long adjustAfter;

    private String canpayTag;

    private String billPayTag;

    private Date recvTime;

    private Date updateTime;

    private String updateStaffId;

    private String rsrvInfo1;

    private String rsrvInfo2;

    //联合查询需要，手工添加
    private DItemExt dItemExt;  //联合查询LAO_D_ITEM表
    
    private static final long serialVersionUID = 1L;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Short getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Short partitionId) {
        this.partitionId = partitionId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getbDiscnt() {
        return bDiscnt;
    }

    public void setbDiscnt(Long bDiscnt) {
        this.bDiscnt = bDiscnt;
    }

    public Long getaDiscnt() {
        return aDiscnt;
    }

    public void setaDiscnt(Long aDiscnt) {
        this.aDiscnt = aDiscnt;
    }

    public Long getAdjustBefore() {
        return adjustBefore;
    }

    public void setAdjustBefore(Long adjustBefore) {
        this.adjustBefore = adjustBefore;
    }

    public Long getAdjustAfter() {
        return adjustAfter;
    }

    public void setAdjustAfter(Long adjustAfter) {
        this.adjustAfter = adjustAfter;
    }

    public String getCanpayTag() {
        return canpayTag;
    }

    public void setCanpayTag(String canpayTag) {
        this.canpayTag = canpayTag == null ? null : canpayTag.trim();
    }

    public String getBillPayTag() {
        return billPayTag;
    }

    public void setBillPayTag(String billPayTag) {
        this.billPayTag = billPayTag == null ? null : billPayTag.trim();
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

    public String getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(String updateStaffId) {
        this.updateStaffId = updateStaffId == null ? null : updateStaffId.trim();
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
    
    //联合查询需要，手工添加
    public DItemExt getDItemExt() {
        return dItemExt;
    }

    //联合查询需要，手工添加
    public void setDItemExt(DItemExt dItemExt) {
        this.dItemExt = dItemExt;
    }
}
