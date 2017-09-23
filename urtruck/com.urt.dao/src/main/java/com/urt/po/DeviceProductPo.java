package com.urt.po;

import java.io.Serializable;

public class DeviceProductPo implements Serializable {
    private String adid;

    private String imgurl;

    private String adprice;

    private String adname;

    private String adlinkurl;

    private String adlintroduce;

    private String isshow;

    private String paraname1;

    private String paraname2;

    private static final long serialVersionUID = 1L;

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid == null ? null : adid.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getAdprice() {
        return adprice;
    }

    public void setAdprice(String adprice) {
        this.adprice = adprice == null ? null : adprice.trim();
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname == null ? null : adname.trim();
    }

    public String getAdlinkurl() {
        return adlinkurl;
    }

    public void setAdlinkurl(String adlinkurl) {
        this.adlinkurl = adlinkurl == null ? null : adlinkurl.trim();
    }

    public String getAdlintroduce() {
        return adlintroduce;
    }

    public void setAdlintroduce(String adlintroduce) {
        this.adlintroduce = adlintroduce == null ? null : adlintroduce.trim();
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow == null ? null : isshow.trim();
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