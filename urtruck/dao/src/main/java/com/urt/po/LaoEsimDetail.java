package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimDetail implements Serializable {
    private Long id;

    private String lenovoid;

    private String imei;

    private Date startdate;

    private Date enddate;

    private String iccid;

    private String startsurplusflow;

    private String endsurplusflow;

    private String currentuseflow;

    private String operators;

    private String currentisusetag;

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getStartsurplusflow() {
        return startsurplusflow;
    }

    public void setStartsurplusflow(String startsurplusflow) {
        this.startsurplusflow = startsurplusflow == null ? null : startsurplusflow.trim();
    }

    public String getEndsurplusflow() {
        return endsurplusflow;
    }

    public void setEndsurplusflow(String endsurplusflow) {
        this.endsurplusflow = endsurplusflow == null ? null : endsurplusflow.trim();
    }

    public String getCurrentuseflow() {
        return currentuseflow;
    }

    public void setCurrentuseflow(String currentuseflow) {
        this.currentuseflow = currentuseflow == null ? null : currentuseflow.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? null : operators.trim();
    }

    public String getCurrentisusetag() {
        return currentisusetag;
    }

    public void setCurrentisusetag(String currentisusetag) {
        this.currentisusetag = currentisusetag == null ? null : currentisusetag.trim();
    }
}