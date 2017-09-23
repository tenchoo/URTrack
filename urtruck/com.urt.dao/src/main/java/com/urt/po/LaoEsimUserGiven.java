package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimUserGiven implements Serializable {
    private Long userId;

    private String eid;

    private String eidtarger;

    private String lenovoid;

    private String lenovoidtarger;

    private Long goodsid;

    private Date givendate;

    private Date lenovoidtargerisbundingdate;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getEidtarger() {
        return eidtarger;
    }

    public void setEidtarger(String eidtarger) {
        this.eidtarger = eidtarger == null ? null : eidtarger.trim();
    }

    public String getLenovoid() {
        return lenovoid;
    }

    public void setLenovoid(String lenovoid) {
        this.lenovoid = lenovoid == null ? null : lenovoid.trim();
    }

    public String getLenovoidtarger() {
        return lenovoidtarger;
    }

    public void setLenovoidtarger(String lenovoidtarger) {
        this.lenovoidtarger = lenovoidtarger == null ? null : lenovoidtarger.trim();
    }

    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public Date getGivendate() {
        return givendate;
    }

    public void setGivendate(Date givendate) {
        this.givendate = givendate;
    }

    public Date getLenovoidtargerisbundingdate() {
        return lenovoidtargerisbundingdate;
    }

    public void setLenovoidtargerisbundingdate(Date lenovoidtargerisbundingdate) {
        this.lenovoidtargerisbundingdate = lenovoidtargerisbundingdate;
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