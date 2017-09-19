package com.urt.po;

import java.io.Serializable;

public class LaoAlmRuleElemPo implements Serializable {
    private Long ruleTypeId;

    private Long elementId;

    private Integer displayIndex;

    private String validTag;

    private Long channelCustId;

    private String remark;

    private Short elemGroup;

    private static final long serialVersionUID = 1L;

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

    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public String getValidTag() {
        return validTag;
    }

    public void setValidTag(String validTag) {
        this.validTag = validTag == null ? null : validTag.trim();
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getElemGroup() {
        return elemGroup;
    }

    public void setElemGroup(Short elemGroup) {
        this.elemGroup = elemGroup;
    }
}