package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoCustPersonDto implements Serializable {
    private Long custId;

    private String psptTypeCode;

    private String psptId;

    private Date psptEndDate;

    private String psptAddr;

    private String custName;

    private String sex;

    private Date birthday;

    private String birthdayLunar;

    private String birthdayFlag;

    private String postAddress;

    private String postCode;

    private String postPerson;

    private String phone;

    private String faxNbr;

    private String email;

    private String nationalityCode;

    private String localNativeCode;

    private String graduateSchool;

    private String speciality;

    private Date updateTime;

    private String updateStaffId;

    private String updateDepartId;

    private String remark;

    private Integer rsrvNum1;

    private String rsrvStr1;

    private Date rsrvDate3;

    private String rsrvTag3;

    private static final long serialVersionUID = 1L;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getPsptTypeCode() {
        return psptTypeCode;
    }

    public void setPsptTypeCode(String psptTypeCode) {
        this.psptTypeCode = psptTypeCode == null ? null : psptTypeCode.trim();
    }

    public String getPsptId() {
        return psptId;
    }

    public void setPsptId(String psptId) {
        this.psptId = psptId == null ? null : psptId.trim();
    }

    public Date getPsptEndDate() {
        return psptEndDate;
    }

    public void setPsptEndDate(Date psptEndDate) {
        this.psptEndDate = psptEndDate;
    }

    public String getPsptAddr() {
        return psptAddr;
    }

    public void setPsptAddr(String psptAddr) {
        this.psptAddr = psptAddr == null ? null : psptAddr.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthdayLunar() {
        return birthdayLunar;
    }

    public void setBirthdayLunar(String birthdayLunar) {
        this.birthdayLunar = birthdayLunar == null ? null : birthdayLunar.trim();
    }

    public String getBirthdayFlag() {
        return birthdayFlag;
    }

    public void setBirthdayFlag(String birthdayFlag) {
        this.birthdayFlag = birthdayFlag == null ? null : birthdayFlag.trim();
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress == null ? null : postAddress.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getPostPerson() {
        return postPerson;
    }

    public void setPostPerson(String postPerson) {
        this.postPerson = postPerson == null ? null : postPerson.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFaxNbr() {
        return faxNbr;
    }

    public void setFaxNbr(String faxNbr) {
        this.faxNbr = faxNbr == null ? null : faxNbr.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode == null ? null : nationalityCode.trim();
    }

    public String getLocalNativeCode() {
        return localNativeCode;
    }

    public void setLocalNativeCode(String localNativeCode) {
        this.localNativeCode = localNativeCode == null ? null : localNativeCode.trim();
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool == null ? null : graduateSchool.trim();
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality == null ? null : speciality.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
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

    public Date getRsrvDate3() {
        return rsrvDate3;
    }

    public void setRsrvDate3(Date rsrvDate3) {
        this.rsrvDate3 = rsrvDate3;
    }

    public String getRsrvTag3() {
        return rsrvTag3;
    }

    public void setRsrvTag3(String rsrvTag3) {
        this.rsrvTag3 = rsrvTag3 == null ? null : rsrvTag3.trim();
    }
}