package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FlowOperationLog implements Serializable {
    private BigDecimal logid;

    private String floworderid;

    private String requestsystem;

    private String responsesystem;

    private String requestparm;

    private String responseparm;

    private String callserver;

    private Date calltime;

    private String respcode;

    private String respdesc;

    private String callissuccess;

    private String paraname1;

    private String paraname2;

    private String stepstate;

    private static final long serialVersionUID = 1L;

    public BigDecimal getLogid() {
        return logid;
    }

    public void setLogid(BigDecimal logid) {
        this.logid = logid;
    }

    public String getFloworderid() {
        return floworderid;
    }

    public void setFloworderid(String floworderid) {
        this.floworderid = floworderid == null ? null : floworderid.trim();
    }

    public String getRequestsystem() {
        return requestsystem;
    }

    public void setRequestsystem(String requestsystem) {
        this.requestsystem = requestsystem == null ? null : requestsystem.trim();
    }

    public String getResponsesystem() {
        return responsesystem;
    }

    public void setResponsesystem(String responsesystem) {
        this.responsesystem = responsesystem == null ? null : responsesystem.trim();
    }

    public String getRequestparm() {
        return requestparm;
    }

    public void setRequestparm(String requestparm) {
        this.requestparm = requestparm == null ? null : requestparm.trim();
    }

    public String getResponseparm() {
        return responseparm;
    }

    public void setResponseparm(String responseparm) {
        this.responseparm = responseparm == null ? null : responseparm.trim();
    }

    public String getCallserver() {
        return callserver;
    }

    public void setCallserver(String callserver) {
        this.callserver = callserver == null ? null : callserver.trim();
    }

    public Date getCalltime() {
        return calltime;
    }

    public void setCalltime(Date calltime) {
        this.calltime = calltime;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public String getRespdesc() {
        return respdesc;
    }

    public void setRespdesc(String respdesc) {
        this.respdesc = respdesc == null ? null : respdesc.trim();
    }

    public String getCallissuccess() {
        return callissuccess;
    }

    public void setCallissuccess(String callissuccess) {
        this.callissuccess = callissuccess == null ? null : callissuccess.trim();
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

    public String getStepstate() {
        return stepstate;
    }

    public void setStepstate(String stepstate) {
        this.stepstate = stepstate == null ? null : stepstate.trim();
    }
}