package com.urt.dto.unicom;

import java.io.Serializable;

/** 以后为收银台回调传回的参数 **/
public class PayBackDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int payType;//支付方式 8、支付宝、25、微信
	private String _input_charset;//商户网站实用的编码格式、如utf-8、gbk、gb2312等、目前传入utf-8
	private String sign_type;//签名方式 DSA,RSA,MD5等 目前传MD5
	private int payAmount;//支付金额，单位RMB分
	private String merchantOrderId;//收银台合作商户网站唯一订单号
	private String tradeNo;//收银台订单号
	private String userId;//用户id
	private String userName;//用户名
	private String privateAttach;//商户私有信息
	private String bankOrderid;//第三方通道订单号
	private int orderStatus;//订单状态0、初始化，1成功，2失败
	private String orderTime;//订单成功时间
	private String sign;//采用标准MD5签名，编码同意为UTF-8，所有不为空参数均参与签名
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrivateAttach() {
		return privateAttach;
	}
	public void setPrivateAttach(String privateAttach) {
		this.privateAttach = privateAttach;
	}
	public String getBankOrderid() {
		return bankOrderid;
	}
	public void setBankOrderid(String bankOrderid) {
		this.bankOrderid = bankOrderid;
	}
	
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "PayBackDto [payType=" + payType + ", _input_charset="
				+ _input_charset + ", sign_type=" + sign_type + ", payAmount="
				+ payAmount + ", merchantOrderId=" + merchantOrderId
				+ ", tradeNo=" + tradeNo + ", userId=" + userId + ", userName="
				+ userName + ", privateAttach=" + privateAttach
				+ ", bankOrderid=" + bankOrderid + ", orderStatus="
				+ orderStatus + ", orderTime=" + orderTime + ", sign=" + sign
				+ "]";
	}
	
	
	
}
