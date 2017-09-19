package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoIccidAssignLibPo implements Serializable {
    private String iccid;

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

    private String custid;

    private Date inDate;

    private String msisdn;

    private String actived;

    private String batchId;

    private Short testCycle;

    private String vic;

    private String imsi;

    private String buyOrderNo;

    private Long testGoodsRlsId;

    private Long id;

    private String cycleState;

    private static final long serialVersionUID = 1L;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey == null ? null : privatekey.trim();
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getInitproduct() {
        return initproduct;
    }

    public void setInitproduct(String initproduct) {
        this.initproduct = initproduct == null ? null : initproduct.trim();
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus == null ? null : cardstatus.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? null : operators.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1 == null ? null : attribute1.trim();
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1 == null ? null : value1.trim();
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2 == null ? null : attribute2.trim();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2 == null ? null : value2.trim();
    }

    public String getIfMaintenance() {
        return ifMaintenance;
    }

    public void setIfMaintenance(String ifMaintenance) {
        this.ifMaintenance = ifMaintenance == null ? null : ifMaintenance.trim();
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getActived() {
        return actived;
    }

    public void setActived(String actived) {
        this.actived = actived == null ? null : actived.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
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
        this.vic = vic == null ? null : vic.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getBuyOrderNo() {
        return buyOrderNo;
    }

    public void setBuyOrderNo(String buyOrderNo) {
        this.buyOrderNo = buyOrderNo == null ? null : buyOrderNo.trim();
    }

    public Long getTestGoodsRlsId() {
        return testGoodsRlsId;
    }

    public void setTestGoodsRlsId(Long testGoodsRlsId) {
        this.testGoodsRlsId = testGoodsRlsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCycleState() {
        return cycleState;
    }

    public void setCycleState(String cycleState) {
        this.cycleState = cycleState == null ? null : cycleState.trim();
    }
}