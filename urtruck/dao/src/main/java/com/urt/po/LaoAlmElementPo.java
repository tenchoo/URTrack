package com.urt.po;

import java.io.Serializable;

public class LaoAlmElementPo implements Serializable {
    private Long elementId;

    private String elementType;

    private String elementName;

    private String elementDesc;

    private String elementLevel;

    private String elemDisplay;

    private String elementSource;

    private Long pElementId;

    private String displayType;

    private String elementDefault;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType == null ? null : elementType.trim();
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName == null ? null : elementName.trim();
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc == null ? null : elementDesc.trim();
    }

    public String getElementLevel() {
        return elementLevel;
    }

    public void setElementLevel(String elementLevel) {
        this.elementLevel = elementLevel == null ? null : elementLevel.trim();
    }

    public String getElemDisplay() {
        return elemDisplay;
    }

    public void setElemDisplay(String elemDisplay) {
        this.elemDisplay = elemDisplay == null ? null : elemDisplay.trim();
    }

    public String getElementSource() {
        return elementSource;
    }

    public void setElementSource(String elementSource) {
        this.elementSource = elementSource == null ? null : elementSource.trim();
    }

    public Long getpElementId() {
        return pElementId;
    }

    public void setpElementId(Long pElementId) {
        this.pElementId = pElementId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType == null ? null : displayType.trim();
    }

    public String getElementDefault() {
        return elementDefault;
    }

    public void setElementDefault(String elementDefault) {
        this.elementDefault = elementDefault == null ? null : elementDefault.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}