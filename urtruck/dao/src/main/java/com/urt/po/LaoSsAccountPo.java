package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoSsAccountPo implements Serializable {
    private Long acconutId;

    private String loginName;

    private String nickname;

    private String password;

    private String salt;

    private String status;

    private Date createDate;

    private String remark;

    private String relatedType;

    private String relatedId;

    private Long custId;

    private String plainPassword;

    private String fstStruct;

    private String secStruct;

    private static final long serialVersionUID = 1L;

    public Long getAcconutId() {
        return acconutId;
    }

    public void setAcconutId(Long acconutId) {
        this.acconutId = acconutId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType == null ? null : relatedType.trim();
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId == null ? null : relatedId.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword == null ? null : plainPassword.trim();
    }

    public String getFstStruct() {
        return fstStruct;
    }

    public void setFstStruct(String fstStruct) {
        this.fstStruct = fstStruct == null ? null : fstStruct.trim();
    }

    public String getSecStruct() {
        return secStruct;
    }

    public void setSecStruct(String secStruct) {
        this.secStruct = secStruct == null ? null : secStruct.trim();
    }

	@Override
	public String toString() {
		return "LaoSsAccountPo [acconutId=" + acconutId + ", loginName="
				+ loginName + ", nickname=" + nickname + ", password="
				+ password + ", salt=" + salt + ", status=" + status
				+ ", createDate=" + createDate + ", remark=" + remark
				+ ", relatedType=" + relatedType + ", relatedId=" + relatedId
				+ ", custId=" + custId + ", plainPassword=" + plainPassword
				+ ", fstStruct=" + fstStruct + ", secStruct=" + secStruct + "]";
	}
    
    
}