package com.urt.dto.dmp;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPLogDto implements Serializable {
    private Long id;

    private Long custId;

    private Long groupId;

    private String imei;

    private Long strategyId;

    private String triggerCause;

    private String isAgainst;

    private Long operationId;

    private String isSuccess;

    private Date operateTime;

    private String operateType;

    private String operateUser;
    
    private String operationComment;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getTriggerCause() {
        return triggerCause;
    }

    public void setTriggerCause(String triggerCause) {
        this.triggerCause = triggerCause == null ? null : triggerCause.trim();
    }

    public String getIsAgainst() {
        return isAgainst;
    }

    public void setIsAgainst(String isAgainst) {
        this.isAgainst = isAgainst == null ? null : isAgainst.trim();
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser == null ? null : operateUser.trim();
    }

	public String getOperationComment() {
		return operationComment;
	}

	public void setOperationComment(String operationComment) {
		this.operationComment = operationComment;
	}

	@Override
	public String toString() {
		return "LaoDMPLogDto [id=" + id + ", custId=" + custId + ", groupId="
				+ groupId + ", imei=" + imei + ", strategyId=" + strategyId
				+ ", triggerCause=" + triggerCause + ", isAgainst=" + isAgainst
				+ ", operationId=" + operationId + ", isSuccess=" + isSuccess
				+ ", operateTime=" + operateTime + ", operateType="
				+ operateType + ", operateUser=" + operateUser
				+ ", operationComment=" + operationComment + "]";
	}
    
}