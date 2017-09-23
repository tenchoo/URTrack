package com.urt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoPoolUseInfoDto implements Serializable{
	private Long id;

    private String eid;

    private String poolId;

    private BigDecimal usedVolume;

    private BigDecimal remainingVolume;

    private BigDecimal totalVolume;

    private Integer inMonth;

    private Date updateTime;

    private String updateStaffId;
    

    private String queryMonth;
    
    
    private Date monthStart;
    
    private Date monthEnd;

    public Date getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(Date monthStart) {
		this.monthStart = monthStart;
	}

	public Date getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(Date monthEnd) {
		this.monthEnd = monthEnd;
	}

	public String getQueryMonth() {
		return queryMonth;
	}

	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}

	private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId == null ? null : poolId.trim();
    }

    public BigDecimal getUsedVolume() {
        return usedVolume;
    }

    public void setUsedVolume(BigDecimal usedVolume) {
        this.usedVolume = usedVolume;
    }

    public BigDecimal getRemainingVolume() {
        return remainingVolume;
    }

    public void setRemainingVolume(BigDecimal remainingVolume) {
        this.remainingVolume = remainingVolume;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Integer getInMonth() {
        return inMonth;
    }

    public void setInMonth(Integer inMonth) {
        this.inMonth = inMonth;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(String updateStaffId) {
        this.updateStaffId = updateStaffId == null ? null : updateStaffId.trim();
    }

}
