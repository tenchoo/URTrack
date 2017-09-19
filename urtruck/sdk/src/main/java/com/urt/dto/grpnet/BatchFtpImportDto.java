package com.urt.dto.grpnet;

import java.io.Serializable;

public class BatchFtpImportDto implements Serializable {
    private Short tradeTypeCode;

    private String tradeTypeName;

    private String hostIp;

    private String hostPort;

    private String userName;

    private String passwd;

    private String procName;

    private String fileName;

    private String filePath;

    private String bakFilePath;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName == null ? null : tradeTypeName.trim();
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp == null ? null : hostIp.trim();
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort == null ? null : hostPort.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName == null ? null : procName.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getBakFilePath() {
        return bakFilePath;
    }

    public void setBakFilePath(String bakFilePath) {
        this.bakFilePath = bakFilePath == null ? null : bakFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
