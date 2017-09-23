package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoPeripheralSysAccessLog implements Serializable {
    private Long custId;

    private String ipAddress;

    private String userName;

    private String serverName;

    private String isSuccess;

    private String errorCode;

    private String reqInfo;

    private String rspInfo;

    private Date accessDate;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(String reqInfo) {
        this.reqInfo = reqInfo == null ? null : reqInfo.trim();
    }

    public String getRspInfo() {
        return rspInfo;
    }

    public void setRspInfo(String rspInfo) {
        this.rspInfo = rspInfo == null ? null : rspInfo.trim();
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
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