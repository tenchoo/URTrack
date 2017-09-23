package com.urt.dto.http;

import java.io.Serializable;
import java.util.List;

public class CardDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String respCode;
	private String respDesc;
	private String cardTotalNum;//总数量
	private List<CardInfo> cardInfo;//卡列表数据
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getCardTotalNum() {
		return cardTotalNum;
	}
	public void setCardTotalNum(String cardTotalNum) {
		this.cardTotalNum = cardTotalNum;
	}
	public List<CardInfo> getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(List<CardInfo> cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	
}
