package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimTradeFeeSub implements Serializable {
    private Long tradeId;

    private Long goodsId;

    private String oldfee;

    private String fee;

    private String payorderid;

    private Short paytag;

    private Date paydate;

    private String paytype;

    private Date createdate;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getOldfee() {
        return oldfee;
    }

    public void setOldfee(String oldfee) {
        this.oldfee = oldfee == null ? null : oldfee.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getPayorderid() {
        return payorderid;
    }

    public void setPayorderid(String payorderid) {
        this.payorderid = payorderid == null ? null : payorderid.trim();
    }

    public Short getPaytag() {
        return paytag;
    }

    public void setPaytag(Short paytag) {
        this.paytag = paytag;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2 == null ? null : param2.trim();
    }
}