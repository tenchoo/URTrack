package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoIccidAssignBatchDto implements Serializable {

	private Long batchId;

	private Short tradeTypeCode;

	private Integer operatorsId;

	private String enterpriseConsignee;

	private String consigneePhone;

	private String consigneeIdcard;

	private String industryCategory;

	private String industrySegmentation;

	private String deliveryAddress;

	private String simType;

	private String simSize;

	private String simFee;

	private String iccidStart;

	private String iccidEnd;

	private String numberStart;

	private String numberEnd;

	private Date deliveryDate;

	private String consignor;

	private String logisticsCompany;

	private String logisticsNumber;

	private String pullPerson;

	private String pullNumber;

	private Date recvTime;

	private String operId;

	private Long sumNum;

	private Long succNum;

	private Long failNum;

	private String remark;

	private String rsrvInfo1;

	private String rsrvInfo2;

	private String orderFee;

	private String payment;

	private String generateinfor;
	
    private String orderDate;
    
    private String[] iccidStrArry;
    
	public String[] getIccidStrArry() {
		return iccidStrArry;
	}

	public void setIccidStrArry(String[] iccidStrArry) {
		this.iccidStrArry = iccidStrArry;
	}

	private static final long serialVersionUID = 1L;

	
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Short getTradeTypeCode() {
		return tradeTypeCode;
	}

	public void setTradeTypeCode(Short tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}

	public Integer getOperatorsId() {
		return operatorsId;
	}

	public void setOperatorsId(Integer operatorsId) {
		this.operatorsId = operatorsId;
	}

	public String getEnterpriseConsignee() {
		return enterpriseConsignee;
	}

	public void setEnterpriseConsignee(String enterpriseConsignee) {
		this.enterpriseConsignee = enterpriseConsignee == null ? null
				: enterpriseConsignee.trim();
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone == null ? null : consigneePhone
				.trim();
	}

	public String getConsigneeIdcard() {
		return consigneeIdcard;
	}

	public void setConsigneeIdcard(String consigneeIdcard) {
		this.consigneeIdcard = consigneeIdcard == null ? null : consigneeIdcard
				.trim();
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory == null ? null
				: industryCategory.trim();
	}

	public String getIndustrySegmentation() {
		return industrySegmentation;
	}

	public void setIndustrySegmentation(String industrySegmentation) {
		this.industrySegmentation = industrySegmentation == null ? null
				: industrySegmentation.trim();
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress
				.trim();
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType == null ? null : simType.trim();
	}

	public String getSimSize() {
		return simSize;
	}

	public void setSimSize(String simSize) {
		this.simSize = simSize == null ? null : simSize.trim();
	}

	public String getSimFee() {
		return simFee;
	}

	public void setSimFee(String simFee) {
		this.simFee = simFee == null ? null : simFee.trim();
	}

	public String getIccidStart() {
		return iccidStart;
	}

	public void setIccidStart(String iccidStart) {
		this.iccidStart = iccidStart == null ? null : iccidStart.trim();
	}

	public String getIccidEnd() {
		return iccidEnd;
	}

	public void setIccidEnd(String iccidEnd) {
		this.iccidEnd = iccidEnd == null ? null : iccidEnd.trim();
	}

	public String getNumberStart() {
		return numberStart;
	}

	public void setNumberStart(String numberStart) {
		this.numberStart = numberStart == null ? null : numberStart.trim();
	}

	public String getNumberEnd() {
		return numberEnd;
	}

	public void setNumberEnd(String numberEnd) {
		this.numberEnd = numberEnd == null ? null : numberEnd.trim();
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor == null ? null : consignor.trim();
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany == null ? null
				: logisticsCompany.trim();
	}

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber == null ? null : logisticsNumber
				.trim();
	}

	public String getPullPerson() {
		return pullPerson;
	}

	public void setPullPerson(String pullPerson) {
		this.pullPerson = pullPerson == null ? null : pullPerson.trim();
	}

	public String getPullNumber() {
		return pullNumber;
	}

	public void setPullNumber(String pullNumber) {
		this.pullNumber = pullNumber == null ? null : pullNumber.trim();
	}

	public Date getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId == null ? null : operId.trim();
	}

	public Long getSumNum() {
		return sumNum;
	}

	public void setSumNum(Long sumNum) {
		this.sumNum = sumNum;
	}

	public Long getSuccNum() {
		return succNum;
	}

	public void setSuccNum(Long succNum) {
		this.succNum = succNum;
	}

	public Long getFailNum() {
		return failNum;
	}

	public void setFailNum(Long failNum) {
		this.failNum = failNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getRsrvInfo1() {
		return rsrvInfo1;
	}

	public void setRsrvInfo1(String rsrvInfo1) {
		this.rsrvInfo1 = rsrvInfo1 == null ? null : rsrvInfo1.trim();
	}

	public String getRsrvInfo2() {
		return rsrvInfo2;
	}

	public void setRsrvInfo2(String rsrvInfo2) {
		this.rsrvInfo2 = rsrvInfo2 == null ? null : rsrvInfo2.trim();
	}

	public String getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee == null ? null : orderFee.trim();
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment == null ? null : payment.trim();
	}

	public String getGenerateinfor() {
		return generateinfor;
	}

	public void setGenerateinfor(String generateinfor) {
		this.generateinfor = generateinfor == null ? null : generateinfor
				.trim();
	}
}