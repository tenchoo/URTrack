package com.urt.po;

import java.io.Serializable;
import java.util.Date;
//业务台账优惠子表
public class TradeDiscont implements Serializable {
    private Long tradeId;
    //受理月份
    private Short acceptMonth;
    //用户标识
    private Long userId;
    //优惠编码
    private Integer discntId;
    //开始时间
    private Date startDate;
    //商品标识
    private Long goodsId;
    //结束时间
    private Date endDate;
    //修改标志
    private String modifyTag;
    //受理时间
    private Date acceptDate;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDiscntId() {
        return discntId;
    }

    public void setDiscntId(Integer discntId) {
        this.discntId = discntId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag == null ? null : modifyTag.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }
}