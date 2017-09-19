package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class EsimLenovoGoodsId implements Serializable {
    private Long id;

    private String lenovoid;

    private String goodsid;

    private Date createdate;

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

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}