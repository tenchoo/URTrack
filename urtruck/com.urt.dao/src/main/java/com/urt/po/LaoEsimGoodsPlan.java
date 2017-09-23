package com.urt.po;

import java.io.Serializable;

public class LaoEsimGoodsPlan implements Serializable {
    private Long id;

    private String goodsid;

    private String operatorsPid;

    private String plansize;

    private String plandesc;

    private String operators;

    private String goodname;

    private String goodsurl;

    private String goodsprice;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public String getOperatorsPid() {
        return operatorsPid;
    }

    public void setOperatorsPid(String operatorsPid) {
        this.operatorsPid = operatorsPid == null ? null : operatorsPid.trim();
    }

    public String getPlansize() {
        return plansize;
    }

    public void setPlansize(String plansize) {
        this.plansize = plansize == null ? null : plansize.trim();
    }

    public String getPlandesc() {
        return plandesc;
    }

    public void setPlandesc(String plandesc) {
        this.plandesc = plandesc == null ? null : plandesc.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? null : operators.trim();
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname == null ? null : goodname.trim();
    }

    public String getGoodsurl() {
        return goodsurl;
    }

    public void setGoodsurl(String goodsurl) {
        this.goodsurl = goodsurl == null ? null : goodsurl.trim();
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice == null ? null : goodsprice.trim();
    }
}