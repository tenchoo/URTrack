package com.urt.po;

import java.io.Serializable;
import java.util.Date;
//业务台账套餐子表
public class TradeOperatorPlan implements Serializable {
    private Long tradeId;
    //受理月份
    private Short acceptMonth;
    //用户标识
    private Long userId;
    //商品标识
    private Long goodsId;
    //商品中的位置
    private Short goodsIndex;
    //修改标志
    private String modifyTag;
    //基本套餐ID	
    private Integer planId;
    //套餐提供商
    private Integer operatorsId;
    //对端套餐编号
    private String operatorsPid;
    //生效方式
    private String enableTag;
    //失效方式
    private String invalidType;
    //生效开始时间
    private Date startDate;
    //生效截止时间
    private Date endDate;
    //原始接入方式
    private String packageType;
    //套餐类型
    private String planType;
    //受理时间
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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