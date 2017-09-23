package com.urt.po;

import java.io.Serializable;

public class LaoEsimImei implements Serializable {
    private String imei;

    private String devicetype;

    private String devicepic;

    private String devicename;

    private String devicedesc;

    private String paraname1;

    private String paraname2;

    private static final long serialVersionUID = 1L;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getDevicepic() {
        return devicepic;
    }

    public void setDevicepic(String devicepic) {
        this.devicepic = devicepic == null ? null : devicepic.trim();
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename == null ? null : devicename.trim();
    }

    public String getDevicedesc() {
        return devicedesc;
    }

    public void setDevicedesc(String devicedesc) {
        this.devicedesc = devicedesc == null ? null : devicedesc.trim();
    }

    public String getParaname1() {
        return paraname1;
    }

    public void setParaname1(String paraname1) {
        this.paraname1 = paraname1 == null ? null : paraname1.trim();
    }

    public String getParaname2() {
        return paraname2;
    }

    public void setParaname2(String paraname2) {
        this.paraname2 = paraname2 == null ? null : paraname2.trim();
    }
}