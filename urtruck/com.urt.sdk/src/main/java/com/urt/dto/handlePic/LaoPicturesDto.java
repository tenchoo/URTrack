package com.urt.dto.handlePic;

import java.io.Serializable;

public class LaoPicturesDto implements Serializable {
    private Long picId;

    private String picName;

    private byte[] picContent;
    
    private String remark;

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

    public byte[] getPicContent() {
        return picContent;
    }

    public void setPicContent(byte[] picContent) {
        this.picContent = picContent;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
