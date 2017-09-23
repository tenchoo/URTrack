package com.urt.po;

import java.io.Serializable;

public class LaoRuleDef implements Serializable {
    private Long ruleId;

    private Long rulegroupId;

    private String rulegroupName;

    private String ruleName;

    private String condStat;

    private String calFormula;

    private Integer priority;

    private String operId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRulegroupId() {
        return rulegroupId;
    }

    public void setRulegroupId(Long rulegroupId) {
        this.rulegroupId = rulegroupId;
    }

    public String getRulegroupName() {
        return rulegroupName;
    }

    public void setRulegroupName(String rulegroupName) {
        this.rulegroupName = rulegroupName == null ? null : rulegroupName.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getCondStat() {
        return condStat;
    }

    public void setCondStat(String condStat) {
        this.condStat = condStat == null ? null : condStat.trim();
    }

    public String getCalFormula() {
        return calFormula;
    }

    public void setCalFormula(String calFormula) {
        this.calFormula = calFormula == null ? null : calFormula.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
}