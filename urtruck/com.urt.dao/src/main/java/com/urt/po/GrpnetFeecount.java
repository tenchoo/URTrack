package com.urt.po;

import java.io.Serializable;

public class GrpnetFeecount implements Serializable {
    private Integer feecountId;

    private Integer objectId;

    private Short computeMethod;

    private String baseUnit;

    private String unitFee;

    private String minValue;

    private String maxValue;

    private String minPrice;

    private String maxPrice;

    private String topValue;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getFeecountId() {
        return feecountId;
    }

    public void setFeecountId(Integer feecountId) {
        this.feecountId = feecountId;
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

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit == null ? null : baseUnit.trim();
    }

    public String getUnitFee() {
        return unitFee;
    }

    public void setUnitFee(String unitFee) {
        this.unitFee = unitFee == null ? null : unitFee.trim();
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

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice == null ? null : minPrice.trim();
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice == null ? null : maxPrice.trim();
    }

    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue == null ? null : topValue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}