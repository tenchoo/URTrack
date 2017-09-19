package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimUser implements Serializable {
    private Long userId;

    private String lenovoid;

    private String eid;

    private String iccid;

    private Date binddate;

    private Date firstcalltime;

    private Date endcalltime;

    private Short curentuserstatus;

    private String cardstatus;

    private String dpaddress;

    private String profileid;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLenovoid() {
        return lenovoid;
    }

    public void setLenovoid(String lenovoid) {
        this.lenovoid = lenovoid == null ? null : lenovoid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public Date getBinddate() {
        return binddate;
    }

    public void setBinddate(Date binddate) {
        this.binddate = binddate;
    }

    public Date getFirstcalltime() {
        return firstcalltime;
    }

    public void setFirstcalltime(Date firstcalltime) {
        this.firstcalltime = firstcalltime;
    }

    public Date getEndcalltime() {
        return endcalltime;
    }

    public void setEndcalltime(Date endcalltime) {
        this.endcalltime = endcalltime;
    }

    public Short getCurentuserstatus() {
        return curentuserstatus;
    }

    public void setCurentuserstatus(Short curentuserstatus) {
        this.curentuserstatus = curentuserstatus;
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus == null ? null : cardstatus.trim();
    }

    public String getDpaddress() {
        return dpaddress;
    }

    public void setDpaddress(String dpaddress) {
        this.dpaddress = dpaddress == null ? null : dpaddress.trim();
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid == null ? null : profileid.trim();
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