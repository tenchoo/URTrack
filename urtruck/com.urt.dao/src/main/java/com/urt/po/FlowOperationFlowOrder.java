package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class FlowOperationFlowOrder implements Serializable {
    private String floworderid;

    private String custchannelid;

    private String clientorderid;

    private String mobile;

    private String packagesize;

    private String stepstate1;

    private Date stepstatetime1;

    private String stepstate2;

    private Date stepstatetime2;

    private String stepstate3;

    private Date stepstatetime3;

    private String callbackdiscount;

    private String callbackdostmoney;

    private String stepstate4;

    private Date stepstatetime4;

    private String paraname1;

    private String paraname2;

    private String issuccess;

    private static final long serialVersionUID = 1L;

    public String getFloworderid() {
        return floworderid;
    }

    public void setFloworderid(String floworderid) {
        this.floworderid = floworderid == null ? null : floworderid.trim();
    }

    public String getCustchannelid() {
        return custchannelid;
    }

    public void setCustchannelid(String custchannelid) {
        this.custchannelid = custchannelid == null ? null : custchannelid.trim();
    }

    public String getClientorderid() {
        return clientorderid;
    }

    public void setClientorderid(String clientorderid) {
        this.clientorderid = clientorderid == null ? null : clientorderid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPackagesize() {
        return packagesize;
    }

    public void setPackagesize(String packagesize) {
        this.packagesize = packagesize == null ? null : packagesize.trim();
    }

    public String getStepstate1() {
        return stepstate1;
    }

    public void setStepstate1(String stepstate1) {
        this.stepstate1 = stepstate1 == null ? null : stepstate1.trim();
    }

    public Date getStepstatetime1() {
        return stepstatetime1;
    }

    public void setStepstatetime1(Date stepstatetime1) {
        this.stepstatetime1 = stepstatetime1;
    }

    public String getStepstate2() {
        return stepstate2;
    }

    public void setStepstate2(String stepstate2) {
        this.stepstate2 = stepstate2 == null ? null : stepstate2.trim();
    }

    public Date getStepstatetime2() {
        return stepstatetime2;
    }

    public void setStepstatetime2(Date stepstatetime2) {
        this.stepstatetime2 = stepstatetime2;
    }

    public String getStepstate3() {
        return stepstate3;
    }

    public void setStepstate3(String stepstate3) {
        this.stepstate3 = stepstate3 == null ? null : stepstate3.trim();
    }

    public Date getStepstatetime3() {
        return stepstatetime3;
    }

    public void setStepstatetime3(Date stepstatetime3) {
        this.stepstatetime3 = stepstatetime3;
    }

    public String getCallbackdiscount() {
        return callbackdiscount;
    }

    public void setCallbackdiscount(String callbackdiscount) {
        this.callbackdiscount = callbackdiscount == null ? null : callbackdiscount.trim();
    }

    public String getCallbackdostmoney() {
        return callbackdostmoney;
    }

    public void setCallbackdostmoney(String callbackdostmoney) {
        this.callbackdostmoney = callbackdostmoney == null ? null : callbackdostmoney.trim();
    }

    public String getStepstate4() {
        return stepstate4;
    }

    public void setStepstate4(String stepstate4) {
        this.stepstate4 = stepstate4 == null ? null : stepstate4.trim();
    }

    public Date getStepstatetime4() {
        return stepstatetime4;
    }

    public void setStepstatetime4(Date stepstatetime4) {
        this.stepstatetime4 = stepstatetime4;
    }

    public String getParaname1() {
        return paraname1;
    }

    public void setParaname1(String paraname1) {
        this.paraname1 = paraname1 == null ? null : paraname1.trim();
    }

    public String getParaname2() {
        return paraname2;
    }

    public void setParaname2(String paraname2) {
        this.paraname2 = paraname2 == null ? null : paraname2.trim();
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess == null ? null : issuccess.trim();
    }
}