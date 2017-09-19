package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class GoodsDto implements Serializable {
    private Long goodsId;

    private String goodsName;

    private String goodsPic;

    private Integer operatorsId;

    private Date updatedate;

    private Date createdate;

    private String goodsPrices;
    
    private String releaseCycle;

    private Long createStaffId;

    private String goodsDesc;
    
    private String operatorsName;
    
    private String goodsType;

    private String activeWay;

    private Integer goodsReleaseId;
    
    //2017年5月26日14:41:56 sunhao
    private String unit;
    private boolean discount;
    
    public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getActiveWay() {
		return activeWay;
	}

	public void setActiveWay(String activeWay) {
		this.activeWay = activeWay;
	}

	public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	private static final long serialVersionUID = 1L;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic == null ? null : goodsPic.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getGoodsPrices() {
        return goodsPrices;
    }

    public void setGoodsPrices(String goodsPrices) {
        this.goodsPrices = goodsPrices == null ? null : goodsPrices.trim();
    }

    public Long getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(Long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

	public String getReleaseCycle() {
		return releaseCycle;
	}

	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}

	public Integer getGoodsReleaseId() {
		return goodsReleaseId;
	}

	public void setGoodsReleaseId(Integer goodsReleaseId) {
		this.goodsReleaseId = goodsReleaseId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
}