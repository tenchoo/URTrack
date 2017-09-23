package com.urt.dto.http;

import java.io.Serializable;

public class GoodsList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsId;

    private String goodsName;

    private String goodsPic;
    private String goodsPrices;

    private String attrValue1;
    
    private String attrValue2;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}

	public String getGoodsPrices() {
		return goodsPrices;
	}

	public void setGoodsPrices(String goodsPrices) {
		this.goodsPrices = goodsPrices;
	}

	public String getAttrValue1() {
		return attrValue1;
	}

	public void setAttrValue1(String attrValue1) {
		this.attrValue1 = attrValue1;
	}

	public String getAttrValue2() {
		return attrValue2;
	}

	public void setAttrValue2(String attrValue2) {
		this.attrValue2 = attrValue2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
