package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChargeOrder implements Serializable {
    private Short id;

    private String username;

    private String userid;

    private String iccid;

    private String orderid;

    private String flowsize;

    private Double payamount;

    private int paystatus;

    private int chargestatus;

    private Date createdate;

    private Date updatedate;

    private Date chargedate;

    private int paytype;

    private String payorderid;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getFlowsize() {
        return flowsize;
    }

    public void setFlowsize(String flowsize) {
        this.flowsize = flowsize == null ? null : flowsize.trim();
    }

    public Double getPayamount() {
		return payamount;
	}

	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}

	public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getChargedate() {
        return chargedate;
    }

    public void setChargedate(Date chargedate) {
        this.chargedate = chargedate;
    }

    public int getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}

	public int getChargestatus() {
		return chargestatus;
	}

	public void setChargestatus(int chargestatus) {
		this.chargestatus = chargestatus;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public String getPayorderid() {
        return payorderid;
    }

    public void setPayorderid(String payorderid) {
        this.payorderid = payorderid == null ? null : payorderid.trim();
    }

	@Override
	public String toString() {
		return "ChargeOrder [id=" + id + ", username=" + username + ", userid="
				+ userid + ", iccid=" + iccid + ", orderid=" + orderid
				+ ", flowsize=" + flowsize + ", payamount=" + payamount
				+ ", paystatus=" + paystatus + ", chargestatus=" + chargestatus
				+ ", createdate=" + createdate + ", updatedate=" + updatedate
				+ ", chargedate=" + chargedate + ", paytype=" + paytype
				+ ", payorderid=" + payorderid + "]";
	}
    
}