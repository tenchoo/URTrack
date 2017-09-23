package com.urt.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 运营商字典表
 * @author liuxl
 *
 */
public class Operators implements Serializable {
	
	//运营商ID
    private Integer operatorsId;

    //运营商名称
    private String operatorsName;

    //父级运营商
    private Integer operatorsP;

    //更新时间
    private Date updatedate;

    //创建时间
    private Date createdate;

    //卡状态变更实现类
    private String statusDealClass;

    //用户套餐订购实现类
    private String planDealClass;

    //流量查询实现类
    private String trafficQueryClass;

    //电话号码查询实现类
    private String queryTelClass;

    //发送短信实现类
    private String sendSemClass;

    //查询卡状态
    private String queryCardStatus;

    //批量查询任务实现类
    private String batchQueryClass;
    
    //是否异步
    private String async;

    private static final long serialVersionUID = 1L;

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getOperatorsName() {
        return operatorsName;
    }

    public void setOperatorsName(String operatorsName) {
        this.operatorsName = operatorsName == null ? null : operatorsName.trim();
    }

    public Integer getOperatorsP() {
        return operatorsP;
    }

    public void setOperatorsP(Integer operatorsP) {
        this.operatorsP = operatorsP;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStatusDealClass() {
        return statusDealClass;
    }

    public void setStatusDealClass(String statusDealClass) {
        this.statusDealClass = statusDealClass == null ? null : statusDealClass.trim();
    }

    public String getPlanDealClass() {
        return planDealClass;
    }

    public void setPlanDealClass(String planDealClass) {
        this.planDealClass = planDealClass == null ? null : planDealClass.trim();
    }

    public String getTrafficQueryClass() {
        return trafficQueryClass;
    }

    public void setTrafficQueryClass(String trafficQueryClass) {
        this.trafficQueryClass = trafficQueryClass == null ? null : trafficQueryClass.trim();
    }

    public String getQueryTelClass() {
        return queryTelClass;
    }

    public void setQueryTelClass(String queryTelClass) {
        this.queryTelClass = queryTelClass == null ? null : queryTelClass.trim();
    }

    public String getSendSemClass() {
        return sendSemClass;
    }

    public void setSendSemClass(String sendSemClass) {
        this.sendSemClass = sendSemClass == null ? null : sendSemClass.trim();
    }

    public String getQueryCardStatus() {
        return queryCardStatus;
    }

    public void setQueryCardStatus(String queryCardStatus) {
        this.queryCardStatus = queryCardStatus == null ? null : queryCardStatus.trim();
    }

    public String getBatchQueryClass() {
        return batchQueryClass;
    }

    public void setBatchQueryClass(String batchQueryClass) {
        this.batchQueryClass = batchQueryClass == null ? null : batchQueryClass.trim();
    }

	public String getAsync() {
		return async;
	}

	public void setAsync(String async) {
		this.async = async;
	}
    
    
}