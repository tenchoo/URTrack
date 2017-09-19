package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUserGoods implements Serializable {
    private Long userId;

    private Long goodsId;

    private Date startDate;

    private Date endDate;

    private Date updateDate;

    private Long biRulesId;

    private Integer goodsReleaseId;
    
    private String releaseCycle;
    
    private Date startUseDate;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getBiRulesId() {
        return biRulesId;
    }

    public void setBiRulesId(Long biRulesId) {
        this.biRulesId = biRulesId;
    }

    public Integer getGoodsReleaseId() {
        return goodsReleaseId;
    }

    public void setGoodsReleaseId(Integer goodsReleaseId) {
        this.goodsReleaseId = goodsReleaseId;
    }

	public String getReleaseCycle() {
		return releaseCycle;
	}

	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}

	public Date getStartUseDate() {
		return startUseDate;
	}

	public void setStartUseDate(Date startUseDate) {
		this.startUseDate = startUseDate;
	}
    
    
}