package com.urt.dto.http.mno;

import java.io.Serializable;
import java.util.Date;

public class LaoMnoAccessLogDto implements Serializable {
    private Long logId;

    private String systemId;

    private String ipAddress;

    private String serverName;

    private String requestId;

    private String iccid;

    private String msisdn;

    private String isSuccess;

    private Date requestTimes;

    private Date responseTimes;

    private Long handleTimes;

    private String respCode;

    private String respDesc;

    private String tradeId;

    private String remarks;

    private byte[] requestInfo;

    private byte[] respInfo;

    private byte[] respText;

    private static final long serialVersionUID = 1L;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
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

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }

    public Date getRequestTimes() {
        return requestTimes;
    }

    public void setRequestTimes(Date requestTimes) {
        this.requestTimes = requestTimes;
    }

    public Date getResponseTimes() {
        return responseTimes;
    }

    public void setResponseTimes(Date responseTimes) {
        this.responseTimes = responseTimes;
    }

    public Long getHandleTimes() {
        return handleTimes;
    }

    public void setHandleTimes(Long handleTimes) {
        this.handleTimes = handleTimes;
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

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public byte[] getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(byte[] requestInfo) {
        this.requestInfo = requestInfo;
    }

    public byte[] getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(byte[] respInfo) {
        this.respInfo = respInfo;
    }

    public byte[] getRespText() {
        return respText;
    }

    public void setRespText(byte[] respText) {
        this.respText = respText;
    }
}