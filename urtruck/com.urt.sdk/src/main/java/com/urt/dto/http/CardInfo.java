package com.urt.dto.http;

import java.io.Serializable;

public class CardInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryOne;//一级分类
	private String categoryTwo;//二级分类
	private String cardState;//卡状态
	private String cardNum;//卡数量
	public String getCategoryOne() {
		return categoryOne;
	}
	public void setCategoryOne(String categoryOne) {
		this.categoryOne = categoryOne;
	}
	public String getCategoryTwo() {
		return categoryTwo;
	}
	public void setCategoryTwo(String categoryTwo) {
		this.categoryTwo = categoryTwo;
	}
	public String getCardState() {
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	
	
}
