package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUserIpManager implements Serializable {
	
	private Long  id;

	private Long custId;

    private String ipAddress;

    private Date updatDate;

    private Date creatDate;

    private String paraName1;

    private String paraName2;

    private Short isdisabled;
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    private static final long serialVersionUID = 1L;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Date getUpdatDate() {
        return updatDate;
    }

    public void setUpdatDate(Date updatDate) {
        this.updatDate = updatDate;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getParaName1() {
        return paraName1;
    }

    public void setParaName1(String paraName1) {
        this.paraName1 = paraName1 == null ? null : paraName1.trim();
    }

    public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }

    public Short getIsdisabled() {
        return isdisabled;
    }

    public void setIsdisabled(Short isdisabled) {
        this.isdisabled = isdisabled;
    }
}