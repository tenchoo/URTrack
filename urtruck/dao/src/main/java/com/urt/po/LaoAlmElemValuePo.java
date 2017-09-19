package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoAlmElemValuePo implements Serializable {
    private Long ruleId;

    private Long ruleTypeId;

    private Long elementId;

    private String elemValue;

    private String validTag;

    private Date recvTime;

    private String recvOperId;

    private Date updateTime;

    private String updateOperId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(Long ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public String getElemValue() {
        return elemValue;
    }

    public void setElemValue(String elemValue) {
        this.elemValue = elemValue == null ? null : elemValue.trim();
    }

    public String getValidTag() {
        return validTag;
    }

    public void setValidTag(String validTag) {
        this.validTag = validTag == null ? null : validTag.trim();
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
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

    public String getUpdateOperId() {
        return updateOperId;
    }

    public void setUpdateOperId(String updateOperId) {
        this.updateOperId = updateOperId == null ? null : updateOperId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}