package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoCustConcatPo implements Serializable {
	private Long contactId;

    private Long custId;

    private String contactName;

    private String contactPhone;

    private String contactFax;

    private String contactEmail;

    private String contactPostCode;

    private String contactPostAddr;

    private String contactHomeAddr;

    private String contactPsptTypeCode;

    private String contactPsptId;

    private String contactWorkName;

    private String contactWorkAddr;

    private String contactDepart;

    private String contactDuty;

    private String bestContactTime;

    private Date updateTime;

    private String updateStaffId;

    private String updateDepartId;

    private String remark;

    private Integer rsrvNum1;

    private String rsrvStr1;

    private Date rsrvDate1;

    private String rsrvTag1;

    private static final long serialVersionUID = 1L;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax == null ? null : contactFax.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public String getContactPostCode() {
        return contactPostCode;
    }

    public void setContactPostCode(String contactPostCode) {
        this.contactPostCode = contactPostCode == null ? null : contactPostCode.trim();
    }

    public String getContactPostAddr() {
        return contactPostAddr;
    }

    public void setContactPostAddr(String contactPostAddr) {
        this.contactPostAddr = contactPostAddr == null ? null : contactPostAddr.trim();
    }

    public String getContactHomeAddr() {
        return contactHomeAddr;
    }

    public void setContactHomeAddr(String contactHomeAddr) {
        this.contactHomeAddr = contactHomeAddr == null ? null : contactHomeAddr.trim();
    }

    public String getContactPsptTypeCode() {
        return contactPsptTypeCode;
    }

    public void setContactPsptTypeCode(String contactPsptTypeCode) {
        this.contactPsptTypeCode = contactPsptTypeCode == null ? null : contactPsptTypeCode.trim();
    }

    public String getContactPsptId() {
        return contactPsptId;
    }

    public void setContactPsptId(String contactPsptId) {
        this.contactPsptId = contactPsptId == null ? null : contactPsptId.trim();
    }

    public String getContactWorkName() {
        return contactWorkName;
    }

    public void setContactWorkName(String contactWorkName) {
        this.contactWorkName = contactWorkName == null ? null : contactWorkName.trim();
    }

    public String getContactWorkAddr() {
        return contactWorkAddr;
    }

    public void setContactWorkAddr(String contactWorkAddr) {
        this.contactWorkAddr = contactWorkAddr == null ? null : contactWorkAddr.trim();
    }

    public String getContactDepart() {
        return contactDepart;
    }

    public void setContactDepart(String contactDepart) {
        this.contactDepart = contactDepart == null ? null : contactDepart.trim();
    }

    public String getContactDuty() {
        return contactDuty;
    }

    public void setContactDuty(String contactDuty) {
        this.contactDuty = contactDuty == null ? null : contactDuty.trim();
    }

    /*public Date getBestContactTime() {
        return bestContactTime;
    }

    public void setBestContactTime(Date bestContactTime) {
        this.bestContactTime = bestContactTime;
    }*/
    

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getBestContactTime() {
		return bestContactTime;
	}

	public void setBestContactTime(String bestContactTime) {
		this.bestContactTime = bestContactTime;
	}

	public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(String updateStaffId) {
        this.updateStaffId = updateStaffId == null ? null : updateStaffId.trim();
    }

    public String getUpdateDepartId() {
        return updateDepartId;
    }

    public void setUpdateDepartId(String updateDepartId) {
        this.updateDepartId = updateDepartId == null ? null : updateDepartId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRsrvNum1() {
        return rsrvNum1;
    }

    public void setRsrvNum1(Integer rsrvNum1) {
        this.rsrvNum1 = rsrvNum1;
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1 == null ? null : rsrvStr1.trim();
    }

    public Date getRsrvDate1() {
        return rsrvDate1;
    }

    public void setRsrvDate1(Date rsrvDate1) {
        this.rsrvDate1 = rsrvDate1;
    }

    public String getRsrvTag1() {
        return rsrvTag1;
    }

    public void setRsrvTag1(String rsrvTag1) {
        this.rsrvTag1 = rsrvTag1 == null ? null : rsrvTag1.trim();
    }
}