package com.urt.dto.unicom;

import java.io.Serializable;

/** 以后为收银台回调传回的参数 **/
public class PaymentDto implements Serializable {

	private static final long serialVersionUID = 1L;
	// 支付状态 1 成功 2 失败
	private Integer orderStatus;
	// lao充值的订单号
	private String merchantOrderId;
	// 支付方式
	private Integer payType;
	// 支付订单号
	private String orderId;
	private String lenovoId;
	private String orderTime;
	private String realm;
	private String sign;
	private String privateAttach;
	private String bankOrderId;// 第三方支付订单号
	private Double payAmount;
	private String flowSize;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLenovoId() {
		return lenovoId;
	}

	public void setLenovoId(String lenovoId) {
		this.lenovoId = lenovoId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPrivateAttach() {
		return privateAttach;
	}

	public void setPrivateAttach(String privateAttach) {
		this.privateAttach = privateAttach;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getFlowSize() {
		return flowSize;
	}

	public void setFlowSize(String flowSize) {
		this.flowSize = flowSize;
	}

	@Override
	public String toString() {
		return "PaymentDto [orderStatus=" + orderStatus + ", merchantOrderId="
				+ merchantOrderId + ", payType=" + payType + ", orderId="
				+ orderId + ", lenovoId=" + lenovoId + ", orderTime="
				+ orderTime + ", realm=" + realm + ", sign=" + sign
				+ ", privateAttach=" + privateAttach + ", bankOrderId="
				+ bankOrderId + ", payAmount=" + payAmount + ", flowSize="
				+ flowSize + "]";
	}

}
