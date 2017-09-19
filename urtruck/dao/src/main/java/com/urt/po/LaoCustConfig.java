package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoCustConfig implements Serializable {
    private Long id;

    private Long custId;

    private String vists;

    private String isTag;

    private Date createdate;

    private String isRandomCheck;

    private String isIpCheck;

    private String isLimitCustVists;

    private String paraName1;

    private String paraName2;

    private String serverName;

    private String serverNameLimit;

    private String sendsmsCallbackurl;

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

    public String getVists() {
        return vists;
    }

    public void setVists(String vists) {
        this.vists = vists == null ? null : vists.trim();
    }

    public String getIsTag() {
        return isTag;
    }

    public void setIsTag(String isTag) {
        this.isTag = isTag == null ? null : isTag.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getIsRandomCheck() {
        return isRandomCheck;
    }

    public void setIsRandomCheck(String isRandomCheck) {
        this.isRandomCheck = isRandomCheck == null ? null : isRandomCheck.trim();
    }

    public String getIsIpCheck() {
        return isIpCheck;
    }

    public void setIsIpCheck(String isIpCheck) {
        this.isIpCheck = isIpCheck == null ? null : isIpCheck.trim();
    }

    public String getIsLimitCustVists() {
        return isLimitCustVists;
    }

    public void setIsLimitCustVists(String isLimitCustVists) {
        this.isLimitCustVists = isLimitCustVists == null ? null : isLimitCustVists.trim();
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerNameLimit() {
        return serverNameLimit;
    }

    public void setServerNameLimit(String serverNameLimit) {
        this.serverNameLimit = serverNameLimit == null ? null : serverNameLimit.trim();
    }

    public String getSendsmsCallbackurl() {
        return sendsmsCallbackurl;
    }

    public void setSendsmsCallbackurl(String sendsmsCallbackurl) {
        this.sendsmsCallbackurl = sendsmsCallbackurl == null ? null : sendsmsCallbackurl.trim();
    }
}