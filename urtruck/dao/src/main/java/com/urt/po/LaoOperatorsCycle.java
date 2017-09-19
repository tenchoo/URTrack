package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoOperatorsCycle implements Serializable {
    private Integer cycId;

    private String idType;

    private String idValue;

    private Short cycleDay;

    private Date startDate;

    private Date endDate;

    private Date updateTime;

    private String updateAccountId;

    private String remark;

    private String monthAcctStatus;

    private static final long serialVersionUID = 1L;

    public Integer getCycId() {
        return cycId;
    }

    public void setCycId(Integer cycId) {
        this.cycId = cycId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue == null ? null : idValue.trim();
    }

    public Short getCycleDay() {
        return cycleDay;
    }

    public void setCycleDay(Short cycleDay) {
        this.cycleDay = cycleDay;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMonthAcctStatus() {
        return monthAcctStatus;
    }

    public void setMonthAcctStatus(String monthAcctStatus) {
        this.monthAcctStatus = monthAcctStatus == null ? null : monthAcctStatus.trim();
    }
}