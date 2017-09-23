package com.urt.po;

import java.io.Serializable;

public class ImeiLibrary implements Serializable {
    private Long id;

    private String imei;

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

	@Override
	public String toString() {
		return "ImeiLibrary [id=" + id + ", imei=" + imei + "]";
	}
    
}