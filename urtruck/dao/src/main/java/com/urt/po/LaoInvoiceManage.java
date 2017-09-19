package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoInvoiceManage implements Serializable {
	/*申请发票ID*/
    private Long invoiceId;
    /*企业客户ID*/
    private Long channelCustId;
    /*个人客户ID*/
    private Long custId;
    /*发票抬头*/
    private String invoiceRise;
    /*识别号*/
    private BigDecimal invoiceNumber;
    /*创建时间*/
    private Date createDate;
    /*更新时间*/
    private Date updateDate;
    /*备用字段1*/
    private String invoiceStr1;
    /*备用字段2*/
    private String invoiceStr2;

    private static final long serialVersionUID = 1L;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise == null ? null : invoiceRise.trim();
    }

    public BigDecimal getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(BigDecimal invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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