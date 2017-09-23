package com.urt.po;

import java.io.Serializable;

public class LaoDCommparaPo implements Serializable {
    private Long paraId;

    private String paraName;

    private String paraType;

    private String paraCode;

    private String paraCode1;

    private String paraCode2;

    private String paraCode3;

    private String paraCode4;

    private String paraCode5;

    private String paraCode6;

    private String useTag;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getParaId() {
        return paraId;
    }

    public void setParaId(Long paraId) {
        this.paraId = paraId;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName == null ? null : paraName.trim();
    }

    public String getParaType() {
        return paraType;
    }

    public void setParaType(String paraType) {
        this.paraType = paraType == null ? null : paraType.trim();
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode == null ? null : paraCode.trim();
    }

    public String getParaCode1() {
        return paraCode1;
    }

    public void setParaCode1(String paraCode1) {
        this.paraCode1 = paraCode1 == null ? null : paraCode1.trim();
    }

    public String getParaCode2() {
        return paraCode2;
    }

    public void setParaCode2(String paraCode2) {
        this.paraCode2 = paraCode2 == null ? null : paraCode2.trim();
    }

    public String getParaCode3() {
        return paraCode3;
    }

    public void setParaCode3(String paraCode3) {
        this.paraCode3 = paraCode3 == null ? null : paraCode3.trim();
    }

    public String getParaCode4() {
        return paraCode4;
    }

    public void setParaCode4(String paraCode4) {
        this.paraCode4 = paraCode4 == null ? null : paraCode4.trim();
    }

    public String getParaCode5() {
        return paraCode5;
    }

    public void setParaCode5(String paraCode5) {
        this.paraCode5 = paraCode5 == null ? null : paraCode5.trim();
    }

    public String getParaCode6() {
        return paraCode6;
    }

    public void setParaCode6(String paraCode6) {
        this.paraCode6 = paraCode6 == null ? null : paraCode6.trim();
    }

    public String getUseTag() {
        return useTag;
    }

    public void setUseTag(String useTag) {
        this.useTag = useTag == null ? null : useTag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}