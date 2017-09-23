package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class GoodsRelease implements Serializable {
    private Integer goodsReleaseId;

    private Long goodsId;

    private Date startDate;

    private Date endDate;

    private Long channelGroupId;

    private String groupAttrN1;

    private String groupAttrV1;

    private String groupAttrN2;

    private String groupAttrV2;

    private String releasePrice;

    private String releaseCycle;

    private Long poolId;

    private String unit;

    private Integer basePlanId;

    private Short sms;

    private Short speechSounds;

    private String silentPeriod;

    private Short extensionModel;

    private Short discountLevel;

    private Short discountRate;

    private Short excessTactics;

    private Short excessRate;

    private Short isAllNormPlan;

    private Short isBindImei;

    private Short isPrivateNetwork;

    private String apnRealm;

    private Short isDefaultPlan;

    private String remark;
    
    private String pname;
    private String custName;
    private String goodsName;
    private String startDateValue;
    private String endDateValue;
    private String ssq;
    private String ssw;
    private String sse;
    private String ssr;
    private Integer ruleId;
    private Long relsCustId;
    private Long parRelsId;
    private String  goodsReleaseName;
    public Long getRelsCustId() {
		return relsCustId;
	}

	public void setRelsCustId(Long relsCustId) {
		this.relsCustId = relsCustId;
	}

	public Long getParRelsId() {
		return parRelsId;
	}

	public void setParRelsId(Long parRelsId) {
		this.parRelsId = parRelsId;
	}

	public String getGoodsReleaseName() {
		return goodsReleaseName;
	}

	public void setGoodsReleaseName(String goodsReleaseName) {
		this.goodsReleaseName = goodsReleaseName;
	}
    private static final long serialVersionUID = 1L;

    public Integer getGoodsReleaseId() {
        return goodsReleaseId;
    }

    public void setGoodsReleaseId(Integer goodsReleaseId) {
        this.goodsReleaseId = goodsReleaseId;
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

    public Long getChannelGroupId() {
        return channelGroupId;
    }

    public void setChannelGroupId(Long channelGroupId) {
        this.channelGroupId = channelGroupId;
    }

    public String getGroupAttrN1() {
        return groupAttrN1;
    }

    public void setGroupAttrN1(String groupAttrN1) {
        this.groupAttrN1 = groupAttrN1 == null ? null : groupAttrN1.trim();
    }

    public String getGroupAttrV1() {
        return groupAttrV1;
    }

    public void setGroupAttrV1(String groupAttrV1) {
        this.groupAttrV1 = groupAttrV1 == null ? null : groupAttrV1.trim();
    }

    public String getGroupAttrN2() {
        return groupAttrN2;
    }

    public void setGroupAttrN2(String groupAttrN2) {
        this.groupAttrN2 = groupAttrN2 == null ? null : groupAttrN2.trim();
    }

    public String getGroupAttrV2() {
        return groupAttrV2;
    }

    public void setGroupAttrV2(String groupAttrV2) {
        this.groupAttrV2 = groupAttrV2 == null ? null : groupAttrV2.trim();
    }

    public String getReleasePrice() {
        return releasePrice;
    }

    public void setReleasePrice(String releasePrice) {
        this.releasePrice = releasePrice == null ? null : releasePrice.trim();
    }

    public String getReleaseCycle() {
        return releaseCycle;
    }

    public void setReleaseCycle(String releaseCycle) {
        this.releaseCycle = releaseCycle == null ? null : releaseCycle.trim();
    }

    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getBasePlanId() {
        return basePlanId;
    }

    public void setBasePlanId(Integer basePlanId) {
        this.basePlanId = basePlanId;
    }

    public Short getSms() {
        return sms;
    }

    public void setSms(Short sms) {
        this.sms = sms;
    }

    public Short getSpeechSounds() {
        return speechSounds;
    }

    public void setSpeechSounds(Short speechSounds) {
        this.speechSounds = speechSounds;
    }

    public String getSilentPeriod() {
        return silentPeriod;
    }

    public void setSilentPeriod(String silentPeriod) {
        this.silentPeriod = silentPeriod == null ? null : silentPeriod.trim();
    }

    public Short getExtensionModel() {
        return extensionModel;
    }

    public void setExtensionModel(Short extensionModel) {
        this.extensionModel = extensionModel;
    }

    public Short getDiscountLevel() {
        return discountLevel;
    }

    public void setDiscountLevel(Short discountLevel) {
        this.discountLevel = discountLevel;
    }

    public Short getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Short discountRate) {
        this.discountRate = discountRate;
    }

    public Short getExcessTactics() {
        return excessTactics;
    }

    public void setExcessTactics(Short excessTactics) {
        this.excessTactics = excessTactics;
    }

    public Short getExcessRate() {
        return excessRate;
    }

    public void setExcessRate(Short excessRate) {
        this.excessRate = excessRate;
    }

    public Short getIsAllNormPlan() {
        return isAllNormPlan;
    }

    public void setIsAllNormPlan(Short isAllNormPlan) {
        this.isAllNormPlan = isAllNormPlan;
    }

    public Short getIsBindImei() {
        return isBindImei;
    }

    public void setIsBindImei(Short isBindImei) {
        this.isBindImei = isBindImei;
    }

    public Short getIsPrivateNetwork() {
        return isPrivateNetwork;
    }

    public void setIsPrivateNetwork(Short isPrivateNetwork) {
        this.isPrivateNetwork = isPrivateNetwork;
    }

    public String getApnRealm() {
        return apnRealm;
    }

    public void setApnRealm(String apnRealm) {
        this.apnRealm = apnRealm == null ? null : apnRealm.trim();
    }

    public Short getIsDefaultPlan() {
        return isDefaultPlan;
    }

    public void setIsDefaultPlan(Short isDefaultPlan) {
        this.isDefaultPlan = isDefaultPlan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getSsq() {
		return ssq;
	}

	public void setSsq(String ssq) {
		this.ssq = ssq;
	}

	public String getSsw() {
		return ssw;
	}

	public void setSsw(String ssw) {
		this.ssw = ssw;
	}

	public String getSse() {
		return sse;
	}

	public void setSse(String sse) {
		this.sse = sse;
	}

	public String getSsr() {
		return ssr;
	}

	public void setSsr(String ssr) {
		this.ssr = ssr;
	}

	public String getStartDateValue() {
		return startDateValue;
	}

	public void setStartDateValue(String startDateValue) {
		this.startDateValue = startDateValue;
	}

	public String getEndDateValue() {
		return endDateValue;
	}

	public void setEndDateValue(String endDateValue) {
		this.endDateValue = endDateValue;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
    
}