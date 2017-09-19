package com.urt.dto;

import java.io.Serializable;

public class LaoAlmRuleTypeDto implements Serializable {
    private Long ruleTypeId;

    private String ruleTypeName;

    private String ruleTypeDesc;

    private String ruleLevel;

    private Long rulePId;

    private String validTag;

    private String exeTag;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(Long ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public String getRuleTypeName() {
        return ruleTypeName;
    }

    public void setRuleTypeName(String ruleTypeName) {
        this.ruleTypeName = ruleTypeName == null ? null : ruleTypeName.trim();
    }

    public String getRuleTypeDesc() {
        return ruleTypeDesc;
    }

    public void setRuleTypeDesc(String ruleTypeDesc) {
        this.ruleTypeDesc = ruleTypeDesc == null ? null : ruleTypeDesc.trim();
    }

    public String getRuleLevel() {
        return ruleLevel;
    }

    public void setRuleLevel(String ruleLevel) {
        this.ruleLevel = ruleLevel == null ? null : ruleLevel.trim();
    }

    public Long getRulePId() {
        return rulePId;
    }

    public void setRulePId(Long rulePId) {
        this.rulePId = rulePId;
    }

    public String getValidTag() {
        return validTag;
    }

    public void setValidTag(String validTag) {
        this.validTag = validTag == null ? null : validTag.trim();
    }

    public String getExeTag() {
        return exeTag;
    }

    public void setExeTag(String exeTag) {
        this.exeTag = exeTag == null ? null : exeTag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}