package com.urt.po;

import java.io.Serializable;
import java.util.Date;
//业务台账商品子表
public class TradeGoods implements Serializable {
    private Long tradeId;
    //受理月份
    private Short acceptMonth;
    //商品标识
    private Long goodsId;
    //开始时间
    private Date startDate;
    //用户标识
    private Long userId;
    //修改标志
    private String modifyTag;
    //结束时间
    private Date endDate;
    //业务执行规则
    private Long biRulesId;
    //业务受理时间
    private Date tradeDate;
    //受理时间
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
		this.modifyTag = modifyTag;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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