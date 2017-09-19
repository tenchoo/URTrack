package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoExcpDef implements Serializable {
    private Short excpTypeCode;

    private String excpName;

    private String excpDesc;

    private Short tradeTypeCode;

    private String excpTag;

    private String excpLevel;

    private String doTag;

    private String doType;

    private String doClass;

    private String doFunc;

    private Short doTimes;

    private String callbackClass;

    private String callbackFunc;

    private Long nextCode;

    private Date updateTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Short getExcpTypeCode() {
        return excpTypeCode;
    }

    public void setExcpTypeCode(Short excpTypeCode) {
        this.excpTypeCode = excpTypeCode;
    }

    public String getExcpName() {
        return excpName;
    }

    public void setExcpName(String excpName) {
        this.excpName = excpName == null ? null : excpName.trim();
    }

    public String getExcpDesc() {
        return excpDesc;
    }

    public void setExcpDesc(String excpDesc) {
        this.excpDesc = excpDesc == null ? null : excpDesc.trim();
    }

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getExcpTag() {
        return excpTag;
    }

    public void setExcpTag(String excpTag) {
        this.excpTag = excpTag == null ? null : excpTag.trim();
    }

    public String getExcpLevel() {
        return excpLevel;
    }

    public void setExcpLevel(String excpLevel) {
        this.excpLevel = excpLevel == null ? null : excpLevel.trim();
    }

    public String getDoTag() {
        return doTag;
    }

    public void setDoTag(String doTag) {
        this.doTag = doTag == null ? null : doTag.trim();
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType == null ? null : doType.trim();
    }

    public String getDoClass() {
        return doClass;
    }

    public void setDoClass(String doClass) {
        this.doClass = doClass == null ? null : doClass.trim();
    }

    public String getDoFunc() {
        return doFunc;
    }

    public void setDoFunc(String doFunc) {
        this.doFunc = doFunc == null ? null : doFunc.trim();
    }

    public Short getDoTimes() {
        return doTimes;
    }

    public void setDoTimes(Short doTimes) {
        this.doTimes = doTimes;
    }

    public String getCallbackClass() {
        return callbackClass;
    }

    public void setCallbackClass(String callbackClass) {
        this.callbackClass = callbackClass == null ? null : callbackClass.trim();
    }

    public String getCallbackFunc() {
        return callbackFunc;
    }

    public void setCallbackFunc(String callbackFunc) {
        this.callbackFunc = callbackFunc == null ? null : callbackFunc.trim();
    }

    public Long getNextCode() {
        return nextCode;
    }

    public void setNextCode(Long nextCode) {
        this.nextCode = nextCode;
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