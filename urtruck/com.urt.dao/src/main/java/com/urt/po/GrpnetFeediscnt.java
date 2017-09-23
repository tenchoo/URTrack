package com.urt.po;

import java.io.Serializable;

public class GrpnetFeediscnt implements Serializable {
    private Integer feediscntId;

    private Integer objectId;

    private Short computeMethod;

    private String minValue;

    private String maxValue;

    private String diviedChildValue;

    private String diviedParentValue;

    private String discntFee;

    private String baseFee;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getFeediscntId() {
        return feediscntId;
    }

    public void setFeediscntId(Integer feediscntId) {
        this.feediscntId = feediscntId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Short getComputeMethod() {
        return computeMethod;
    }

    public void setComputeMethod(Short computeMethod) {
        this.computeMethod = computeMethod;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue == null ? null : minValue.trim();
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue == null ? null : maxValue.trim();
    }

    public String getDiviedChildValue() {
        return diviedChildValue;
    }

    public void setDiviedChildValue(String diviedChildValue) {
        this.diviedChildValue = diviedChildValue == null ? null : diviedChildValue.trim();
    }

    public String getDiviedParentValue() {
        return diviedParentValue;
    }

    public void setDiviedParentValue(String diviedParentValue) {
        this.diviedParentValue = diviedParentValue == null ? null : diviedParentValue.trim();
    }

    public String getDiscntFee() {
        return discntFee;
    }

    public void setDiscntFee(String discntFee) {
        this.discntFee = discntFee == null ? null : discntFee.trim();
    }

    public String getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(String baseFee) {
        this.baseFee = baseFee == null ? null : baseFee.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}