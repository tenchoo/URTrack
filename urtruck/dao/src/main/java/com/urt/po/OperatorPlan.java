package com.urt.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有运营商计费单元
 * @author liuxl
 *
 */
public class OperatorPlan implements Serializable {

	//主键
	private Integer planId;

	//计费单元名称
    private String planName;

    //运营商编号
    private Integer operators;

    //运营商的计费id
    private String operatorsPid;

    //有效时长
    private Integer timeLength;

    //时长单位
    private String timeUnit;

    //最大量
    private Integer quantityMax;

    //量单位
    private String quantityUnit;

    //过期时间
    private Date expDate;

    //套餐状态
    private String planState;

    //生效方式 立即生效：0 下账期生效：1 申请时立即生效：2
    private String enableTag;

    //失效方式 包月：1 流量包：0
    private String invalidType;

    //套餐类型
    private String planType;

    //成本价
    private String costPrice;

    //
    private String planClassify;


    private static final long serialVersionUID = 1L;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getOperators() {
        return operators;
    }

    public void setOperators(Integer operators) {
        this.operators = operators;
    }

    public String getOperatorsPid() {
        return operatorsPid;
    }

    public void setOperatorsPid(String operatorsPid) {
        this.operatorsPid = operatorsPid == null ? null : operatorsPid.trim();
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit == null ? null : timeUnit.trim();
    }

    public Integer getQuantityMax() {
        return quantityMax;
    }

    public void setQuantityMax(Integer quantityMax) {
        this.quantityMax = quantityMax;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit == null ? null : quantityUnit.trim();
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState == null ? null : planState.trim();
    }

    public String getEnableTag() {
        return enableTag;
    }

    public void setEnableTag(String enableTag) {
        this.enableTag = enableTag == null ? null : enableTag.trim();
    }

    public String getInvalidType() {
        return invalidType;
    }

    public void setInvalidType(String invalidType) {
        this.invalidType = invalidType == null ? null : invalidType.trim();
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice == null ? null : costPrice.trim();
    }

    public String getPlanClassify() {
        return planClassify;
    }

    public void setPlanClassify(String planClassify) {
        this.planClassify = planClassify == null ? null : planClassify.trim();
    }

}