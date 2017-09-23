package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimImeiEid implements Serializable {
    private Long id;

    private String eid;

    private String imeiid;

    private String imeiname;

    private Date createdate;

    private Date updatedate;

    private String param1;

    private String param2;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getImeiid() {
        return imeiid;
    }

    public void setImeiid(String imeiid) {
        this.imeiid = imeiid == null ? null : imeiid.trim();
    }

    public String getImeiname() {
        return imeiname;
    }

    public void setImeiname(String imeiname) {
        this.imeiname = imeiname == null ? null : imeiname.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
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