package com.urt.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 每天手工更新数据日志表
 * @author liuxl
 *
 */
public class LaoDataImportLog implements Serializable {
	
	//数据入表流水
    private Long impId;

    //日志数据类型：1-流量数据；2-资费生失效时间数据
    private String impType;

    //数据类型名称
    private String impName;

    //数据更新时间，yyyymmdd
    private String impDate;

    //数据运营商区分
    private String operatorType;

    //区分名称
    private String operatorName;

    //运营商id,对应lao_operators.operators_id
    private Integer operatorsId;

    //更新时间
    private Date updateTime;

    //更新人员
    private String updateStaff;

    //备注
    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getImpId() {
        return impId;
    }

    public void setImpId(Long impId) {
        this.impId = impId;
    }

    public String getImpType() {
        return impType;
    }

    public void setImpType(String impType) {
        this.impType = impType == null ? null : impType.trim();
    }

    public String getImpName() {
        return impName;
    }

    public void setImpName(String impName) {
        this.impName = impName == null ? null : impName.trim();
    }

    public String getImpDate() {
        return impDate;
    }

    public void setImpDate(String impDate) {
        this.impDate = impDate == null ? null : impDate.trim();
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType == null ? null : operatorType.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(String updateStaff) {
        this.updateStaff = updateStaff == null ? null : updateStaff.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}