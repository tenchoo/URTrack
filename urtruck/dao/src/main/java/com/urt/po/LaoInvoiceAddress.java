package com.urt.po;

import java.io.Serializable;

public class LaoInvoiceAddress implements Serializable {
	/*发票投递主键*/
    private Long addressId;
    /*企业客户ID*/
    private Long chanalCustId;
    /*个人客户ID*/
    private Long custId;
    /*收件人*/
    private String receivePerson;
    /*寄件人*/
    private String shipPerson;
    /*地址*/
    private String receiveAddress;
    /*电话*/
    private Long telephone;
    /*邮政编码*/
    private Long postcode;
    /*是否为默认地址*/
    private Short isdefault;
    /*备注*/
    private String remark;
    /*备用字段1*/
    private String invoiceStr1;
    /*备用字段2*/
    private String invoiceStr2;

    private static final long serialVersionUID = 1L;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getChanalCustId() {
        return chanalCustId;
    }

    public void setChanalCustId(Long chanalCustId) {
        this.chanalCustId = chanalCustId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson == null ? null : receivePerson.trim();
    }

    public String getShipPerson() {
        return shipPerson;
    }

    public void setShipPerson(String shipPerson) {
        this.shipPerson = shipPerson == null ? null : shipPerson.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Long getPostcode() {
        return postcode;
    }

    public void setPostcode(Long postcode) {
        this.postcode = postcode;
    }

    public Short getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Short isdefault) {
        this.isdefault = isdefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getInvoiceStr1() {
        return invoiceStr1;
    }

    public void setInvoiceStr1(String invoiceStr1) {
        this.invoiceStr1 = invoiceStr1 == null ? null : invoiceStr1.trim();
    }

    public String getInvoiceStr2() {
        return invoiceStr2;
    }

    public void setInvoiceStr2(String invoiceStr2) {
        this.invoiceStr2 = invoiceStr2 == null ? null : invoiceStr2.trim();
    }
}