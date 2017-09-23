package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoIccidAssignBatch implements Serializable {
	// 批次流水号
	private Long batchId;

	// 业务类型编码
	private Short tradeTypeCode;

	// 运营商id
	private Integer operatorsId;

	// 企业收货人
	private String enterpriseConsignee;

	// 收货人电话
	private String consigneePhone;

	// 收货人身份证
	private String consigneeIdcard;

	// 行业类别
	private String industryCategory;

	// 行业细分
	private String industrySegmentation;

	// 收货地址
	private String deliveryAddress;

	// SIM卡类型
	private String simType;

	// SIM卡尺寸
	private String simSize;

	// SIM卡卡费用（元/张）
	private String simFee;

	// 起始ICCID
	private String iccidStart;

	// 终止ICCID
	private String iccidEnd;

	// 分配起始号码
	private String numberStart;

	// 分配终止号码
	private String numberEnd;

	// 发货日期
	private Date deliveryDate;

	// 发货人
	private String consignor;

	// 物流公司
	private String logisticsCompany;

	// 物流编号
	private String logisticsNumber;

	// 揽件人
	private String pullPerson;

	// 揽件人电话
	private String pullNumber;

	// 入表时间
	private Date recvTime;

	// 操作人员
	private String operId;

	// 总记录
	private Long sumNum;

	// 成功数量
	private Long succNum;

	// 失败数量
	private Long failNum;

	// 备注
	private String remark;

	// 保留信息1
	private String rsrvInfo1;

	// 保留信息2
	private String rsrvInfo2;

	// 订单总金额
	private String orderFee;

	// 是否已缴费：0->已缴费；1->未缴费
	private String payment;

	// 是否生成在维用户信息：0->是，1->否
	private String generateinfor;

	// 订单日期
	private String orderDate;

	private static final long serialVersionUID = 1L;

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
		this.enterpriseConsignee = enterpriseConsignee == null ? null : enterpriseConsignee.trim();
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone == null ? null : consigneePhone.trim();
	}

	public String getConsigneeIdcard() {
		return consigneeIdcard;
	}

	public void setConsigneeIdcard(String consigneeIdcard) {
		this.consigneeIdcard = consigneeIdcard == null ? null : consigneeIdcard.trim();
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory == null ? null : industryCategory.trim();
	}

	public String getIndustrySegmentation() {
		return industrySegmentation;
	}

	public void setIndustrySegmentation(String industrySegmentation) {
		this.industrySegmentation = industrySegmentation == null ? null : industrySegmentation.trim();
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
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
		this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
	}

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber == null ? null : logisticsNumber.trim();
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
		this.generateinfor = generateinfor == null ? null : generateinfor.trim();
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate == null ? null : orderDate.trim();
	}
}