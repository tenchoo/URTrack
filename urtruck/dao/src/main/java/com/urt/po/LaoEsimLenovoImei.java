package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoEsimLenovoImei implements Serializable {
    private Long id;

    private String lenovoid;

    private String imei;

    private Date createdate;

    private Date endtime;

    private String flag;

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

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }
}