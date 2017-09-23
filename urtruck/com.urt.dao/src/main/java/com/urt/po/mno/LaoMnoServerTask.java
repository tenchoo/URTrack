package com.urt.po.mno;

import java.io.Serializable;
import java.util.Date;

public class LaoMnoServerTask implements Serializable {
    private Long taskId;

    private String systemId;

    private String srcRequestId;

    private Long accessLogId;

    private String comRequestId;

    private String comServerName;

    private String serverName;

    private String taskStatus;

    private String respCode;

    private String respDesc;

    private String iccid;

    private String msisdn;

    private Short repeatTimes;

    private Short priorityLevel;

    private Date createDate;

    private Date updateDate;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getSrcRequestId() {
        return srcRequestId;
    }

    public void setSrcRequestId(String srcRequestId) {
        this.srcRequestId = srcRequestId == null ? null : srcRequestId.trim();
    }

    public Long getAccessLogId() {
        return accessLogId;
    }

    public void setAccessLogId(Long accessLogId) {
        this.accessLogId = accessLogId;
    }

    public String getComRequestId() {
        return comRequestId;
    }

    public void setComRequestId(String comRequestId) {
        this.comRequestId = comRequestId == null ? null : comRequestId.trim();
    }

    public String getComServerName() {
        return comServerName;
    }

    public void setComServerName(String comServerName) {
        this.comServerName = comServerName == null ? null : comServerName.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc == null ? null : respDesc.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Short getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Short repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public Short getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Short priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getParaName1() {
        return paraName1;
    }

    public void setParaName1(String paraName1) {
        this.paraName1 = paraName1 == null ? null : paraName1.trim();
    }

    public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }
}