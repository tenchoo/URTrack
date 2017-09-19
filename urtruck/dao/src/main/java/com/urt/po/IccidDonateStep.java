package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IccidDonateStep implements Serializable {
	private Short id;

	private String stepid;

	private String userid;

	private String iccid;

	private String plan;

	private String totalflowsize;

	private Integer totalstep;

	private Integer currentstep;

	private Integer remainstep;

	private Date createtime;

	private Date updatetime;

	private static final long serialVersionUID = 1L;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getStepid() {
		return stepid;
	}

	public void setStepid(String stepid) {
		this.stepid = stepid == null ? null : stepid.trim();
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

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan == null ? null : plan.trim();
	}

	public String getTotalflowsize() {
		return totalflowsize;
	}

	public void setTotalflowsize(String totalflowsize) {
		this.totalflowsize = totalflowsize == null ? null : totalflowsize
				.trim();
	}

	public Integer getTotalstep() {
		return totalstep;
	}

	public void setTotalstep(Integer totalstep) {
		this.totalstep = totalstep;
	}

	public Integer getCurrentstep() {
		return currentstep;
	}

	public void setCurrentstep(Integer currentstep) {
		this.currentstep = currentstep;
	}

	public Integer getRemainstep() {
		return remainstep;
	}

	public void setRemainstep(Integer remainstep) {
		this.remainstep = remainstep;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "IccidDonateStep [id=" + id + ", stepid=" + stepid + ", userid="
				+ userid + ", iccid=" + iccid + ", plan=" + plan
				+ ", totalflowsize=" + totalflowsize + ", totalstep="
				+ totalstep + ", currentstep=" + currentstep + ", remainstep="
				+ remainstep + ", createtime=" + createtime + ", updatetime="
				+ updatetime + "]";
	}
	
}