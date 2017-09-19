package com.urt.dto.Trade;

import java.io.Serializable;
import java.util.Date;

public class TradeGoodsDto implements Serializable {
    private Long tradeId;

    private Short acceptMonth;

    private Long goodsId;

    private Date startDate;

    private Long userId;

    private String modifyTag;

    private Date endDate;

    private Long biRulesId;

    private Date tradeDate;

    private Date acceptDate;
    
    private Integer goodsReleaseId;
    
    private String releaseCycle;

    private static final long serialVersionUID = 1L;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Short getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(Short acceptMonth) {
        this.acceptMonth = acceptMonth;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag == null ? null : modifyTag.trim();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getBiRulesId() {
        return biRulesId;
    }

    public void setBiRulesId(Long biRulesId) {
        this.biRulesId = biRulesId;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
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
    
    
}