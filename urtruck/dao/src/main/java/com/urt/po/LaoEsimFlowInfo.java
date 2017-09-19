package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimFlowInfo implements Serializable {
    private Long id;

    private String lenovoid;

    private String totalflow;

    private String surplusflow;

    private String operators;

    private Date createdate;

    private Date updatedate;

    private String imsi;

    private String goodsid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLenovoid() {
        return lenovoid;
    }

    public void setLenovoid(String lenovoid) {
        this.lenovoid = lenovoid == null ? null : lenovoid.trim();
    }

    public String getTotalflow() {
        return totalflow;
    }

    public void setTotalflow(String totalflow) {
        this.totalflow = totalflow == null ? null : totalflow.trim();
    }

    public String getSurplusflow() {
        return surplusflow;
    }

    public void setSurplusflow(String surplusflow) {
        this.surplusflow = surplusflow == null ? null : surplusflow.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? null : operators.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }
}