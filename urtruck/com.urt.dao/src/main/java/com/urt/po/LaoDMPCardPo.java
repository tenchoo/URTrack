package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPCardPo implements Serializable {
    private Long id;

    private String iccid;

    private String imei;

    private String imsi;

    private String phonenumber;

    private Integer simstate;

    private String networkoperatorname;

    private Integer devicetype;

    private Date createtime;

    private Date starttime;

    private Date endtime;

    private Date updatetime;

    private Short flag;

    private Short triggedreason;

    private Long custid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Integer getSimstate() {
        return simstate;
    }

    public void setSimstate(Integer simstate) {
        this.simstate = simstate;
    }

    public String getNetworkoperatorname() {
        return networkoperatorname;
    }

    public void setNetworkoperatorname(String networkoperatorname) {
        this.networkoperatorname = networkoperatorname == null ? null : networkoperatorname.trim();
    }

    public Integer getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(Integer devicetype) {
        this.devicetype = devicetype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }

    public Short getTriggedreason() {
        return triggedreason;
    }

    public void setTriggedreason(Short triggedreason) {
        this.triggedreason = triggedreason;
    }

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }
}