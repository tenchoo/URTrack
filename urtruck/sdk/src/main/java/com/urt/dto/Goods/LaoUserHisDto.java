package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class LaoUserHisDto implements Serializable {
    private Long seqId;

    private Long userId;

    private Long channelCustId;

    private Long custId;

    private String msisdn;

    private Integer operatorsId;

    private Long scoreValue;

    private Integer creditClass;

    private Long basicCreditValue;

    private Long creditValue;

    private String acctTag;

    private String prepayTag;

    private Date inDate;

    private Date openDate;

    private String removeTag;

    private Date destroyTime;

    private Date preDestroyTime;

    private Date firstCallTime;

    private Date lastStopTime;

    private String iccid;

    private String deviceId;

    private String statusCode;

    private Short aliveCheckTime;

    private String imei;

    private String stateCode;

    private String attribute1;

    private String value1;

    private String attribute2;

    private String value2;

    private String operType;
    
    private String tradeTypeCode;

    private Date modifyTime;

    private Long accountId;

    private static final long serialVersionUID = 1L;
    

    public LaoUserHisDto() {
    }

    
    public LaoUserHisDto(LaoUserDto laoUserDto, String operType, String tradeTypeCode, Date modifyTime, Long accountId) {
        this.userId = laoUserDto.getUserId();
        this.channelCustId = laoUserDto.getChannelCustId();
        this.custId = laoUserDto.getCustId();
        this.msisdn = laoUserDto.getMsisdn();
        this.operatorsId = laoUserDto.getOperatorsId();
        this.scoreValue = laoUserDto.getScoreValue();
        this.creditClass = laoUserDto.getCreditClass();
        this.basicCreditValue = laoUserDto.getBasicCreditValue();
        this.creditValue = laoUserDto.getCreditValue();
        this.acctTag = laoUserDto.getAcctTag();
        this.prepayTag = laoUserDto.getPrepayTag();
        this.inDate = laoUserDto.getInDate();
        this.openDate = laoUserDto.getOpenDate();
        this.removeTag = laoUserDto.getRemoveTag();
        this.destroyTime = laoUserDto.getDestroyTime();
        this.preDestroyTime = laoUserDto.getPreDestroyTime();
        this.firstCallTime = laoUserDto.getFirstCallTime();
        this.lastStopTime = laoUserDto.getLastStopTime();
        this.iccid = laoUserDto.getIccid();
        this.deviceId = laoUserDto.getDeviceId();
        this.statusCode = laoUserDto.getStatusCode();
        this.aliveCheckTime = laoUserDto.getAliveCheckTime();
        this.imei = laoUserDto.getImei();
        this.stateCode = laoUserDto.getStateCode();
        this.attribute1 = laoUserDto.getAttribute1();
        this.value1 = laoUserDto.getValue1();
        this.attribute2 = laoUserDto.getAttribute2();
        this.value2 = laoUserDto.getValue2();
        this.operType = operType;
        this.tradeTypeCode = tradeTypeCode;
        this.modifyTime = modifyTime;
        this.accountId = accountId;
    }


    public String getTradeTypeCode() {
        return tradeTypeCode;
    }


    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }


    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public Long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Long scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Integer getCreditClass() {
        return creditClass;
    }

    public void setCreditClass(Integer creditClass) {
        this.creditClass = creditClass;
    }

    public Long getBasicCreditValue() {
        return basicCreditValue;
    }

    public void setBasicCreditValue(Long basicCreditValue) {
        this.basicCreditValue = basicCreditValue;
    }

    public Long getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Long creditValue) {
        this.creditValue = creditValue;
    }

    public String getAcctTag() {
        return acctTag;
    }

    public void setAcctTag(String acctTag) {
        this.acctTag = acctTag == null ? null : acctTag.trim();
    }

    public String getPrepayTag() {
        return prepayTag;
    }

    public void setPrepayTag(String prepayTag) {
        this.prepayTag = prepayTag == null ? null : prepayTag.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getRemoveTag() {
        return removeTag;
    }

    public void setRemoveTag(String removeTag) {
        this.removeTag = removeTag == null ? null : removeTag.trim();
    }

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public Date getPreDestroyTime() {
        return preDestroyTime;
    }

    public void setPreDestroyTime(Date preDestroyTime) {
        this.preDestroyTime = preDestroyTime;
    }

    public Date getFirstCallTime() {
        return firstCallTime;
    }

    public void setFirstCallTime(Date firstCallTime) {
        this.firstCallTime = firstCallTime;
    }

    public Date getLastStopTime() {
        return lastStopTime;
    }

    public void setLastStopTime(Date lastStopTime) {
        this.lastStopTime = lastStopTime;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public Short getAliveCheckTime() {
        return aliveCheckTime;
    }

    public void setAliveCheckTime(Short aliveCheckTime) {
        this.aliveCheckTime = aliveCheckTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1 == null ? null : attribute1.trim();
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1 == null ? null : value1.trim();
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2 == null ? null : attribute2.trim();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2 == null ? null : value2.trim();
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
