package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoReptInfoPo implements Serializable {
    private Long reptId;

    private Short tradeTypeCode;

    private String reptName;

    private String displayInfo;

    private String colIds;

    private String colAttr;

    private String sqlInfo;

    private String condCols;

    private String condSource;

    private String displayType;

    private String validTag;

    private Date recvTime;

    private Date updateTime;

    private String remark;

    private String colKey;

    private static final long serialVersionUID = 1L;

    public Long getReptId() {
        return reptId;
    }

    public void setReptId(Long reptId) {
        this.reptId = reptId;
    }

    public Short getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(Short tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getReptName() {
        return reptName;
    }

    public void setReptName(String reptName) {
        this.reptName = reptName == null ? null : reptName.trim();
    }

    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo == null ? null : displayInfo.trim();
    }

    public String getColIds() {
        return colIds;
    }

    public void setColIds(String colIds) {
        this.colIds = colIds == null ? null : colIds.trim();
    }

    public String getColAttr() {
        return colAttr;
    }

    public void setColAttr(String colAttr) {
        this.colAttr = colAttr == null ? null : colAttr.trim();
    }

    public String getSqlInfo() {
        return sqlInfo;
    }

    public void setSqlInfo(String sqlInfo) {
        this.sqlInfo = sqlInfo == null ? null : sqlInfo.trim();
    }

    public String getCondCols() {
        return condCols;
    }

    public void setCondCols(String condCols) {
        this.condCols = condCols == null ? null : condCols.trim();
    }

    public String getCondSource() {
        return condSource;
    }

    public void setCondSource(String condSource) {
        this.condSource = condSource == null ? null : condSource.trim();
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType == null ? null : displayType.trim();
    }

    public String getValidTag() {
        return validTag;
    }

    public void setValidTag(String validTag) {
        this.validTag = validTag == null ? null : validTag.trim();
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
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

    public String getColKey() {
        return colKey;
    }

    public void setColKey(String colKey) {
        this.colKey = colKey == null ? null : colKey.trim();
    }
}