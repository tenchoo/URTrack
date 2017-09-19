package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoGoodsReleaseRule implements Serializable {
    private Integer ruleId;

    private String ruleName;

    private Long goodsReleaseId;

    private Long custId;

    private Integer packageNumMax;

    private String effectiveMode;

    private Short status;

    private Date endDate;

    private Short monthCycle;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Long getGoodsReleaseId() {
        return goodsReleaseId;
    }

    public void setGoodsReleaseId(Long goodsReleaseId) {
        this.goodsReleaseId = goodsReleaseId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getPackageNumMax() {
        return packageNumMax;
    }

    public void setPackageNumMax(Integer packageNumMax) {
        this.packageNumMax = packageNumMax;
    }

    public String getEffectiveMode() {
        return effectiveMode;
    }

    public void setEffectiveMode(String effectiveMode) {
        this.effectiveMode = effectiveMode == null ? null : effectiveMode.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getMonthCycle() {
        return monthCycle;
    }

    public void setMonthCycle(Short monthCycle) {
        this.monthCycle = monthCycle;
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
}