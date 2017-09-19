package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoSsAccessLogPo implements Serializable {
    private Long logId;

    private String loginId;

    private String loginName;

    private String iccid;

    private String url;

    private String thirdPartyAccountId;

    private String thirdPartyAccountType;

    private Date accessTime;

    private String tradeId;

    private String userIp;

    private static final long serialVersionUID = 1L;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getThirdPartyAccountId() {
        return thirdPartyAccountId;
    }

    public void setThirdPartyAccountId(String thirdPartyAccountId) {
        this.thirdPartyAccountId = thirdPartyAccountId == null ? null : thirdPartyAccountId.trim();
    }

    public String getThirdPartyAccountType() {
        return thirdPartyAccountType;
    }

    public void setThirdPartyAccountType(String thirdPartyAccountType) {
        this.thirdPartyAccountType = thirdPartyAccountType == null ? null : thirdPartyAccountType.trim();
    }

    

    public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }
}