package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class UserOperatorInfoDto implements Serializable {
    private Long id;

    private String iccid;

    private String startTime;

    private String endTime;

    private String operator;

    private String palnCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getPalnCode() {
        return palnCode;
    }

    public void setPalnCode(String palnCode) {
        this.palnCode = palnCode == null ? null : palnCode.trim();
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
}