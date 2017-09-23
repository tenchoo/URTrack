package com.urt.po;

import java.io.Serializable;

public class LaoPicturesPo implements Serializable {
    private Long picId;

    private String picName;

    private String remark;

    private byte[] picContent;

    private static final long serialVersionUID = 1L;

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public byte[] getPicContent() {
        return picContent;
    }

    public void setPicContent(byte[] picContent) {
        this.picContent = picContent;
    }
}