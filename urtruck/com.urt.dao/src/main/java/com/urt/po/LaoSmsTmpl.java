package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoSmsTmpl implements Serializable {
    private Integer templeteId;

    private String smsType;

    private String tmplContext;

    private Date startDate;

    private Date endDate;

    private String smsCorp;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getTempleteId() {
        return templeteId;
    }

    public void setTempleteId(Integer templeteId) {
        this.templeteId = templeteId;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType == null ? null : smsType.trim();
    }

    public String getTmplContext() {
        return tmplContext;
    }

    public void setTmplContext(String tmplContext) {
        this.tmplContext = tmplContext == null ? null : tmplContext.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSmsCorp() {
        return smsCorp;
    }

    public void setSmsCorp(String smsCorp) {
        this.smsCorp = smsCorp == null ? null : smsCorp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}