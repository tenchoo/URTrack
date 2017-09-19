package com.urt.po;

import java.io.Serializable;

public class LaoGoodsOperative implements Serializable {
    private Short id;

    private String pictureurl;

    private String picturelinkurl;

    private Short isshow;

    private String param1;

    private String param2;

    private Short goodsid;

    private Short displaytag;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl == null ? null : pictureurl.trim();
    }

    public String getPicturelinkurl() {
        return picturelinkurl;
    }

    public void setPicturelinkurl(String picturelinkurl) {
        this.picturelinkurl = picturelinkurl == null ? null : picturelinkurl.trim();
    }

    public Short getIsshow() {
        return isshow;
    }

    public void setIsshow(Short isshow) {
        this.isshow = isshow;
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

    public Short getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Short goodsid) {
        this.goodsid = goodsid;
    }

    public Short getDisplaytag() {
        return displaytag;
    }

    public void setDisplaytag(Short displaytag) {
        this.displaytag = displaytag;
    }
}