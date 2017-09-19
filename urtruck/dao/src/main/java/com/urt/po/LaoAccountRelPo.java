package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoAccountRelPo implements Serializable {
    private Long relId;

    private Long accountId;

    private String relType;

    private String relAccount;

    private String relLoginname;

    private String relNickname;

    private String relImgurl;

    private Date recvTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType == null ? null : relType.trim();
    }

    public String getRelAccount() {
        return relAccount;
    }

    public void setRelAccount(String relAccount) {
        this.relAccount = relAccount == null ? null : relAccount.trim();
    }

    public String getRelLoginname() {
        return relLoginname;
    }

    public void setRelLoginname(String relLoginname) {
        this.relLoginname = relLoginname == null ? null : relLoginname.trim();
    }

    public String getRelNickname() {
        return relNickname;
    }

    public void setRelNickname(String relNickname) {
        this.relNickname = relNickname == null ? null : relNickname.trim();
    }

    public String getRelImgurl() {
        return relImgurl;
    }

    public void setRelImgurl(String relImgurl) {
        this.relImgurl = relImgurl == null ? null : relImgurl.trim();
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}