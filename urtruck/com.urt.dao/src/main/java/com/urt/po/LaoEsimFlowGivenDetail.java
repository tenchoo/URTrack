package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimFlowGivenDetail implements Serializable {
    private Long id;

    private String givenlenovoid;

    private String bgivenlenovoid;

    private String flowsize;

    private Date createdate;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenlenovoid() {
        return givenlenovoid;
    }

    public void setGivenlenovoid(String givenlenovoid) {
        this.givenlenovoid = givenlenovoid == null ? null : givenlenovoid.trim();
    }

    public String getBgivenlenovoid() {
        return bgivenlenovoid;
    }

    public void setBgivenlenovoid(String bgivenlenovoid) {
        this.bgivenlenovoid = bgivenlenovoid == null ? null : bgivenlenovoid.trim();
    }

    public String getFlowsize() {
        return flowsize;
    }

    public void setFlowsize(String flowsize) {
        this.flowsize = flowsize == null ? null : flowsize.trim();
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