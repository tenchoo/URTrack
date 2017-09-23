package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoDMPPositionPo implements Serializable {
    private Long id;

    private String imei;

    private BigDecimal latitude;

    private BigDecimal longtitude;

    private String address;

    private BigDecimal speed;

    private String errorinfo;

    private String mcc;

    private String mnc;

    private Integer mbasestationid;

    private Integer msystemid;

    private Integer mnetworkid;

    private Long lac;

    private Long cid;

    private String registeredstate;

    private Date createtime;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(BigDecimal longtitude) {
        this.longtitude = longtitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo == null ? null : errorinfo.trim();
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc == null ? null : mcc.trim();
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc == null ? null : mnc.trim();
    }

    public Integer getMbasestationid() {
        return mbasestationid;
    }

    public void setMbasestationid(Integer mbasestationid) {
        this.mbasestationid = mbasestationid;
    }

    public Integer getMsystemid() {
        return msystemid;
    }

    public void setMsystemid(Integer msystemid) {
        this.msystemid = msystemid;
    }

    public Integer getMnetworkid() {
        return mnetworkid;
    }

    public void setMnetworkid(Integer mnetworkid) {
        this.mnetworkid = mnetworkid;
    }

    public Long getLac() {
        return lac;
    }

    public void setLac(Long lac) {
        this.lac = lac;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getRegisteredstate() {
        return registeredstate;
    }

    public void setRegisteredstate(String registeredstate) {
        this.registeredstate = registeredstate == null ? null : registeredstate.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}