package com.urt.dto.chargeOff;

import java.io.Serializable;
import java.util.Date;

public class LaoOperatorsbillResultDto implements Serializable {
    private Long batchId;

    private Integer cycleId;

    private Integer operatorsId;
    
    private String operatorsName;

    private Long totalCost;

    private Long glaTotalCost;

    private Long sumNum;

    private Long succNum;

    private Long failNum;
    
    private Long unChargeNum;

    private Date updateTime;

    private String updateAccountId;

    private String remark;
    
    private String operateTime;

    private static final long serialVersionUID = 1L;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getGlaTotalCost() {
        return glaTotalCost;
    }

    public void setGlaTotalCost(Long glaTotalCost) {
        this.glaTotalCost = glaTotalCost;
    }

    public Long getSumNum() {
        return sumNum;
    }

    public void setSumNum(Long sumNum) {
        this.sumNum = sumNum;
    }

    public Long getSuccNum() {
        return succNum;
    }

    public void setSuccNum(Long succNum) {
        this.succNum = succNum;
    }

    public Long getFailNum() {
        return failNum;
    }

    public void setFailNum(Long failNum) {
        this.failNum = failNum;
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

	public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public Long getUnChargeNum() {
		return unChargeNum;
	}

	public void setUnChargeNum(Long unChargeNum) {
		this.unChargeNum = unChargeNum;
	}
	
	
    
}
