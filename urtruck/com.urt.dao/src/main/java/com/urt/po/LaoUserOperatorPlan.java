package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUserOperatorPlan implements Serializable {
	private Long userId;

    private Integer planId;

    private Date startDate;

    private int goodsIndex;

    private Long goodsId;

    private Integer operatorsId;

    private String operatorsPid;

    private Date endDate;

    private String planType;

    private String opeartorsDealRst;

    private String opeartorsDealCode;

    private int opeartorsDealNum;

    private Integer goodsReleaseId;
    
    private Date startUseDate;

    private String billTag;

    private Long tradeId;

    private String costPrice;
    
    private String planClassify;


    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    
    public int getGoodsIndex() {
		return goodsIndex;
	}

	public void setGoodsIndex(int goodsIndex) {
		this.goodsIndex = goodsIndex;
	}

	public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
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


    
    public int getOpeartorsDealNum() {
		return opeartorsDealNum;
	}

	public void setOpeartorsDealNum(int opeartorsDealNum) {
		this.opeartorsDealNum = opeartorsDealNum;
	}

	public Integer getGoodsReleaseId() {
        return goodsReleaseId;
    }

    public void setGoodsReleaseId(Integer goodsReleaseId) {
        this.goodsReleaseId = goodsReleaseId;
    }

	public Date getStartUseDate() {
		return startUseDate;
	}

	public void setStartUseDate(Date startUseDate) {
		this.startUseDate = startUseDate;
	}

    public String getBillTag() {
        return billTag;
    }

    public void setBillTag(String billTag) {
        this.billTag = billTag == null ? null : billTag.trim();
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice == null ? null : costPrice.trim();
    }

	public String getPlanClassify() {
		return planClassify;
	}

	public void setPlanClassify(String planClassify) {
		this.planClassify = planClassify;
	}

    
}