package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimUserBunding implements Serializable {
    private Long userId;

    private String eid;

    private String eidtarger;

    private Date unbundlingdate;

    private Date bundlingdate;

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

    public Date getUnbundlingdate() {
        return unbundlingdate;
    }

    public void setUnbundlingdate(Date unbundlingdate) {
        this.unbundlingdate = unbundlingdate;
    }

    public Date getBundlingdate() {
        return bundlingdate;
    }

    public void setBundlingdate(Date bundlingdate) {
        this.bundlingdate = bundlingdate;
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