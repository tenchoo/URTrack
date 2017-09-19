package com.urt.dto.Goods;

import java.io.Serializable;

/**
 * 
 * @author zhaoyf
 * 个人订购
 *
 */
public class PersonalOrderDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389243250765858681L;
	private String iccid;
	/** 运营商 */
	private String operatorName;
	/** 剩余流量 */
	private String dataRemaining;
	/** 渠道客户名称 */
	private String channelCustName;
	/** 卡状态 */
	private String cardState;
	/** 属性名1*/
	private String attribute1;
	/** 属性名2*/
	private String attribute2;
	/** 属性值1*/
	private String value1;
	/** 属性值2*/
	private String value2;
	
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getDataRemaining() {
		return dataRemaining;
	}
	public void setDataRemaining(String dataRemaining) {
		this.dataRemaining = dataRemaining;
	}
	public String getChannelCustName() {
		return channelCustName;
	}
	public void setChannelCustName(String channelCustName) {
		this.channelCustName = channelCustName;
	}
	public String getCardState() {
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}
	
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	

}
