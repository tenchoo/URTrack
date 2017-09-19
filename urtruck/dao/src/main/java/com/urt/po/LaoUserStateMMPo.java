package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoUserStateMMPo implements Serializable {
    private Integer inDate;

    private Long channelCustId;

    private String channelCustName;

    private Long custId;

    private String custName;

    private String staffId;

    private String staffName;

    private String regionId;

    private String regionName;

    private Integer operatorsId;

    private String operatorsName;

    private String value1;

    private String value1Name;

    private String value2;

    private String value2Name;

    private String stateCode;

    private String stateName;

    private String stateAsname;

    private BigDecimal sumFlow;

    private Long cardNum;

    private Date updateTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getInDate() {
        return inDate;
    }

    public void setInDate(Integer inDate) {
        this.inDate = inDate;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public String getChannelCustName() {
        return channelCustName;
    }

    public void setChannelCustName(String channelCustName) {
        this.channelCustName = channelCustName == null ? null : channelCustName.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getOperatorsName() {
        return operatorsName;
    }

    public void setOperatorsName(String operatorsName) {
        this.operatorsName = operatorsName == null ? null : operatorsName.trim();
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1 == null ? null : value1.trim();
    }

    public String getValue1Name() {
        return value1Name;
    }

    public void setValue1Name(String value1Name) {
        this.value1Name = value1Name == null ? null : value1Name.trim();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2 == null ? null : value2.trim();
    }

    public String getValue2Name() {
        return value2Name;
    }

    public void setValue2Name(String value2Name) {
        this.value2Name = value2Name == null ? null : value2Name.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName == null ? null : stateName.trim();
    }

    public String getStateAsname() {
        return stateAsname;
    }

    public void setStateAsname(String stateAsname) {
        this.stateAsname = stateAsname == null ? null : stateAsname.trim();
    }

    public BigDecimal getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(BigDecimal sumFlow) {
        this.sumFlow = sumFlow;
    }

    public Long getCardNum() {
        return cardNum;
    }

    public void setCardNum(Long cardNum) {
        this.cardNum = cardNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}