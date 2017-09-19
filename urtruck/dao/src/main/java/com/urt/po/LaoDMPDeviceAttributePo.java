package com.urt.po;

import java.io.Serializable;

public class LaoDMPDeviceAttributePo implements Serializable {
    private String carddataid;

    private String attributename;

    private String attributevalue;

    private static final long serialVersionUID = 1L;

    public String getCarddataid() {
        return carddataid;
    }

    public void setCarddataid(String carddataid) {
        this.carddataid = carddataid == null ? null : carddataid.trim();
    }

    public String getAttributename() {
        return attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename == null ? null : attributename.trim();
    }

    public String getAttributevalue() {
        return attributevalue;
    }

    public void setAttributevalue(String attributevalue) {
        this.attributevalue = attributevalue == null ? null : attributevalue.trim();
    }

	@Override
	public String toString() {
		return "LaoDMPDeviceAttributePo [carddataid=" + carddataid
				+ ", attributename=" + attributename + ", attributevalue="
				+ attributevalue + "]";
	}
    
}