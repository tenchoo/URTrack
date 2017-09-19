package com.urt.dto.Trade;

import java.io.Serializable;
import java.util.Date;

public class TradeOperatorPlanDto implements Serializable {
    private Long tradeId;

    private Short acceptMonth;

    private Long userId;

    private Long goodsId;

    private Short goodsIndex;

    private String modifyTag;

    private Integer planId;

    private Integer operatorsId;

    private String operatorsPid;

    private String enableTag;

    private String invalidType;

    private Date startDate;

    private Date endDate;

    private String packageType;

    private String planType;

    private Date acceptDate;
    
    private String planClassify;


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

    public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Short getGoodsIndex() {
        return goodsIndex;
    }

    public void setGoodsIndex(Short goodsIndex) {
        this.goodsIndex = goodsIndex;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag == null ? null : modifyTag.trim();
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getOperatorsPid() {
        return operatorsPid;
    }

    public void setOperatorsPid(String operatorsPid) {
        this.operatorsPid = operatorsPid == null ? null : operatorsPid.trim();
    }

    public String getEnableTag() {
        return enableTag;
    }

    public void setEnableTag(String enableTag) {
        this.enableTag = enableTag == null ? null : enableTag.trim();
    }

    public String getInvalidType() {
        return invalidType;
    }

    public void setInvalidType(String invalidType) {
        this.invalidType = invalidType == null ? null : invalidType.trim();
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

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType == null ? null : packageType.trim();
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

	public String getPlanClassify() {
		return planClassify;
	}

	public void setPlanClassify(String planClassify) {
		this.planClassify = planClassify;
	}

}