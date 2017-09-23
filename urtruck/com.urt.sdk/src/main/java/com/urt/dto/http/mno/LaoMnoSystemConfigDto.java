package com.urt.dto.http.mno;

import java.io.Serializable;
import java.util.Date;

public class LaoMnoSystemConfigDto implements Serializable {
    private Long configId;

    private String systemId;

    private String appKey;

    private String ipLimit;

    private String signLimit;

    private String serverContentFormat;

    private String serverDesc;

    private String systemDesc;

    private Date createDate;

    private Date updateDate;

    private String pushUrl;

    private String pushProtocol;

    private String pushContentFormat;

    private String pushEncryptionWay;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getIpLimit() {
        return ipLimit;
    }

    public void setIpLimit(String ipLimit) {
        this.ipLimit = ipLimit == null ? null : ipLimit.trim();
    }

    public String getSignLimit() {
        return signLimit;
    }

    public void setSignLimit(String signLimit) {
        this.signLimit = signLimit == null ? null : signLimit.trim();
    }

    public String getServerContentFormat() {
        return serverContentFormat;
    }

    public void setServerContentFormat(String serverContentFormat) {
        this.serverContentFormat = serverContentFormat == null ? null : serverContentFormat.trim();
    }

    public String getServerDesc() {
        return serverDesc;
    }

    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc == null ? null : serverDesc.trim();
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc == null ? null : systemDesc.trim();
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

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl == null ? null : pushUrl.trim();
    }

    public String getPushProtocol() {
        return pushProtocol;
    }

    public void setPushProtocol(String pushProtocol) {
        this.pushProtocol = pushProtocol == null ? null : pushProtocol.trim();
    }

    public String getPushContentFormat() {
        return pushContentFormat;
    }

    public void setPushContentFormat(String pushContentFormat) {
        this.pushContentFormat = pushContentFormat == null ? null : pushContentFormat.trim();
    }

    public String getPushEncryptionWay() {
        return pushEncryptionWay;
    }

    public void setPushEncryptionWay(String pushEncryptionWay) {
        this.pushEncryptionWay = pushEncryptionWay == null ? null : pushEncryptionWay.trim();
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