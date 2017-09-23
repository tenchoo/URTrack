package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoCustomerStyle implements Serializable {
    private Long custId;

    private String custName;

    private String custLogo;

    private String custStyle;

    private Date creatDate;

    private Short isdisabled;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

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

    public String getCustLogo() {
        return custLogo;
    }

    public void setCustLogo(String custLogo) {
        this.custLogo = custLogo == null ? null : custLogo.trim();
    }

    public String getCustStyle() {
        return custStyle;
    }

    public void setCustStyle(String custStyle) {
        this.custStyle = custStyle == null ? null : custStyle.trim();
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public Short getIsdisabled() {
        return isdisabled;
    }

    public void setIsdisabled(Short isdisabled) {
        this.isdisabled = isdisabled;
    }

    public String getParaName1() {
        return paraName1;
    }

    public void setParaName1(String paraName1) {
        this.paraName1 = paraName1 == null ? null : paraName1.trim();
    }

    public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }
}