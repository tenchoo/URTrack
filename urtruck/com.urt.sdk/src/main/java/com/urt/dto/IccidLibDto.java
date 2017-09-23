package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class IccidLibDto implements Serializable {
	private Long id;

    private String iccid;
    
    private String msisdn;
    
    private String custid;

    private String devicetype;

    private String privatekey;

    private String cardtype;

    private String initproduct;

    private String cardstatus;

    private String operators;

    private String ctype;

    private String attribute1;

    private String value1;

    private String attribute2;

    private String value2;

    private String ifMaintenance;

    private String actived;
    
    private Date inDate;
    
    private Short testCycle;

    private String vic;

    private String imsi;

    private String buyOrderNo;

    private Long testGoodsRlsId;

    private String cycleState;
    //为了前台展示添加的额外字段
    private String custName;
    private String operatorsName;
    private String goodName;
    private String att1;
    private String val1;
    private String att2;
    private String val2;
    private String serviceName;
    private String inTime;
    
    private String initproductname;
    
    private String  testGoodsRlsIdname;
    
    public String getInitproductname() {
		return initproductname;
	}

	public void setInitproductname(String initproductname) {
		this.initproductname = initproductname;
	}

	public String getTestGoodsRlsIdname() {
		return testGoodsRlsIdname;
	}

	public void setTestGoodsRlsIdname(String testGoodsRlsIdname) {
		this.testGoodsRlsIdname = testGoodsRlsIdname;
	}

	/**
	 * @return the inTime
	 */
	public String getInTime() {
		return inTime;
	}

	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	private String batchId;
    
    private static final long serialVersionUID = 1L;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}

	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	/**
	 * @return the devicetype
	 */
	public String getDevicetype() {
		return devicetype;
	}

	/**
	 * @param devicetype the devicetype to set
	 */
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	/**
	 * @return the privatekey
	 */
	public String getPrivatekey() {
		return privatekey;
	}

	/**
	 * @param privatekey the privatekey to set
	 */
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	/**
	 * @return the cardtype
	 */
	public String getCardtype() {
		return cardtype;
	}

	/**
	 * @param cardtype the cardtype to set
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	/**
	 * @return the initproduct
	 */
	public String getInitproduct() {
		return initproduct;
	}

	/**
	 * @param initproduct the initproduct to set
	 */
	public void setInitproduct(String initproduct) {
		this.initproduct = initproduct;
	}

	/**
	 * @return the cardstatus
	 */
	public String getCardstatus() {
		return cardstatus;
	}

	/**
	 * @param cardstatus the cardstatus to set
	 */
	public void setCardstatus(String cardstatus) {
		this.cardstatus = cardstatus;
	}

	/**
	 * @return the operators
	 */
	public String getOperators() {
		return operators;
	}

	/**
	 * @param operators the operators to set
	 */
	public void setOperators(String operators) {
		this.operators = operators;
	}

	/**
	 * @return the ctype
	 */
	public String getCtype() {
		return ctype;
	}

	/**
	 * @param ctype the ctype to set
	 */
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	/**
	 * @return the attribute1
	 */
	public String getAttribute1() {
		return attribute1;
	}

	/**
	 * @param attribute1 the attribute1 to set
	 */
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	/**
	 * @return the value1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * @param value1 the value1 to set
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * @return the attribute2
	 */
	public String getAttribute2() {
		return attribute2;
	}

	/**
	 * @param attribute2 the attribute2 to set
	 */
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	/**
	 * @return the value2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * @return the ifMaintenance
	 */
	public String getIfMaintenance() {
		return ifMaintenance;
	}

	/**
	 * @param ifMaintenance the ifMaintenance to set
	 */
	public void setIfMaintenance(String ifMaintenance) {
		this.ifMaintenance = ifMaintenance;
	}

	/**
	 * @return the custid
	 */
	public String getCustid() {
		return custid;
	}

	/**
	 * @param custid the custid to set
	 */
	public void setCustid(String custid) {
		this.custid = custid;
	}

	/**
	 * @return the inDate
	 */
	public Date getInDate() {
		return inDate;
	}

	/**
	 * @param inDate the inDate to set
	 */
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the operatorsName
	 */
	public String getOperatorsName() {
		return operatorsName;
	}

	/**
	 * @param operatorsName the operatorsName to set
	 */
	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}

	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	/**
	 * @return the att1
	 */
	public String getAtt1() {
		return att1;
	}

	/**
	 * @param att1 the att1 to set
	 */
	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	/**
	 * @return the val1
	 */
	public String getVal1() {
		return val1;
	}

	/**
	 * @param val1 the val1 to set
	 */
	public void setVal1(String val1) {
		this.val1 = val1;
	}

	/**
	 * @return the att2
	 */
	public String getAtt2() {
		return att2;
	}

	/**
	 * @param att2 the att2 to set
	 */
	public void setAtt2(String att2) {
		this.att2 = att2;
	}

	/**
	 * @return the val2
	 */
	public String getVal2() {
		return val2;
	}

	/**
	 * @param val2 the val2 to set
	 */
	public void setVal2(String val2) {
		this.val2 = val2;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the actived
	 */
	public String getActived() {
		return actived;
	}

	/**
	 * @param actived the actived to set
	 */
	public void setActived(String actived) {
		this.actived = actived;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Short getTestCycle() {
		return testCycle;
	}

	public void setTestCycle(Short testCycle) {
		this.testCycle = testCycle;
	}

	public String getVic() {
		return vic;
	}

	public void setVic(String vic) {
		this.vic = vic;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(String buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	public Long getTestGoodsRlsId() {
		return testGoodsRlsId;
	}

	public void setTestGoodsRlsId(Long testGoodsRlsId) {
		this.testGoodsRlsId = testGoodsRlsId;
	}

	public String getCycleState() {
		return cycleState;
	}

	public void setCycleState(String cycleState) {
		this.cycleState = cycleState;
	}
	
	
}