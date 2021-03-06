package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class GoodsElementDto implements Serializable {
    private Integer elementId;

    private String elementType;

    private Integer originalId;

    private Date startDate;

    private Date endDate;

    private String packageType;

    private Short goodsIndex;

    private Long goodsId;
    
    private String planClassify;
    
    private String elementName;   

    public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType == null ? null : elementType.trim();
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
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

    public Short getGoodsIndex() {
        return goodsIndex;
    }

    public void setGoodsIndex(Short goodsIndex) {
        this.goodsIndex = goodsIndex;
    }

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	
	
	public String getPlanClassify() {
		return planClassify;
	}

	public void setPlanClassify(String planClassify) {
		this.planClassify = planClassify;
	}

	@Override
	public String toString() {
		return "GoodsElementDto [elementId=" + elementId + ", elementType="
				+ elementType + ", originalId=" + originalId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", packageType="
				+ packageType + ", goodsIndex=" + goodsIndex + ", goodsId="
				+ goodsId + ", elementName=" + elementName + ",planClassify="+planClassify+"]";
	}
    
    
}