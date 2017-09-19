package com.urt.dto.grpnet;

import java.io.Serializable;
import java.math.BigDecimal;

public class DItemDto implements Serializable {
    private Integer itemId;

    private String itemName;

    private String itemDesc;

    private Integer pItemId;

    private Integer startCycleId;

    private Integer endCycleId;

    private BigDecimal taxRate;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public Integer getpItemId() {
        return pItemId;
    }

    public void setpItemId(Integer pItemId) {
        this.pItemId = pItemId;
    }

    public Integer getStartCycleId() {
        return startCycleId;
    }

    public void setStartCycleId(Integer startCycleId) {
        this.startCycleId = startCycleId;
    }

    public Integer getEndCycleId() {
        return endCycleId;
    }

    public void setEndCycleId(Integer endCycleId) {
        this.endCycleId = endCycleId;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}