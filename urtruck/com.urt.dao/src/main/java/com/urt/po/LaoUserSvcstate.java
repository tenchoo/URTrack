package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUserSvcstate implements Serializable {
    private Long userId;

    private Integer serviceId;

    private Date startDate;

    private Date endDate;

    private Date updateTime;

    private String stateCode;

    private String opeartorsDealRst;

    private String opeartorsDealCode;

    private int opeartorsDealNum;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getOpeartorsDealRst() {
        return opeartorsDealRst;
    }

    public void setOpeartorsDealRst(String opeartorsDealRst) {
        this.opeartorsDealRst = opeartorsDealRst == null ? null : opeartorsDealRst.trim();
    }

    public String getOpeartorsDealCode() {
        return opeartorsDealCode;
    }

    public void setOpeartorsDealCode(String opeartorsDealCode) {
        this.opeartorsDealCode = opeartorsDealCode == null ? null : opeartorsDealCode.trim();
    }

	/**
	 * @return the opeartorsDealNum
	 */
	public int getOpeartorsDealNum() {
		return opeartorsDealNum;
	}

	/**
	 * @param opeartorsDealNum the opeartorsDealNum to set
	 */
	public void setOpeartorsDealNum(int opeartorsDealNum) {
		this.opeartorsDealNum = opeartorsDealNum;
	}

}