package com.urt.dto;

import java.io.Serializable;

public class TfFSsResourceDto implements Serializable {
    private Long resourceId;

    private String resCode;
    
    
    

    private String resName;

    private static final long serialVersionUID = 1L;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }
}