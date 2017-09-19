package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IccidLog implements Serializable {
    private Short logId;

    private String stepid;

    private String userid;

    private String iccid;

    private Integer action;

    private String targetflowsize;

    private String targetapn;

    private Integer actionstatus;

    private Date createtime;

    private static final long serialVersionUID = 1L;

    public Short getLogId() {
        return logId;
    }

    public void setLogId(Short logId) {
        this.logId = logId;
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

    public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getTargetflowsize() {
        return targetflowsize;
    }

    public void setTargetflowsize(String targetflowsize) {
        this.targetflowsize = targetflowsize == null ? null : targetflowsize.trim();
    }

    public String getTargetapn() {
        return targetapn;
    }

    public void setTargetapn(String targetapn) {
        this.targetapn = targetapn == null ? null : targetapn.trim();
    }

    public Integer getActionstatus() {
		return actionstatus;
	}

	public void setActionstatus(Integer actionstatus) {
		this.actionstatus = actionstatus;
	}

	public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}