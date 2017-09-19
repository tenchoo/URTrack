package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimIccidLib implements Serializable {
    private Short id;

    private String iccid;

    private String cardstatus;

    private String countryId;

    private Date indate;

    private Short iccidcurentstatus;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus == null ? null : cardstatus.trim();
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId == null ? null : countryId.trim();
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Short getIccidcurentstatus() {
        return iccidcurentstatus;
    }

    public void setIccidcurentstatus(Short iccidcurentstatus) {
        this.iccidcurentstatus = iccidcurentstatus;
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