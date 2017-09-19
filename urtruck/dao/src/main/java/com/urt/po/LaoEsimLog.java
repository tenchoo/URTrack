package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimLog implements Serializable {
    private Long id;

    private String lenovoid;

    private String eid;

    private String iccid;

    private String requestinfo;

    private String responseinfo;

    private String respcode;

    private Date indate;

    private String tradeTypecode;

    private String param1;

    private String param2;

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

    public String getRequestinfo() {
        return requestinfo;
    }

    public void setRequestinfo(String requestinfo) {
        this.requestinfo = requestinfo == null ? null : requestinfo.trim();
    }

    public String getResponseinfo() {
        return responseinfo;
    }

    public void setResponseinfo(String responseinfo) {
        this.responseinfo = responseinfo == null ? null : responseinfo.trim();
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getTradeTypecode() {
        return tradeTypecode;
    }

    public void setTradeTypecode(String tradeTypecode) {
        this.tradeTypecode = tradeTypecode == null ? null : tradeTypecode.trim();
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