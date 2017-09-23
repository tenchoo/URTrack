package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LaoInvoiceLog implements Serializable {
	/*发票主键ID*/
    private Long invoiceId;
    /*企业ID*/
    private Long channelCustId;
    /*申请类目  0 消费类  */
    private Short applicationType;
    /*发票金额 */
    private Long invoiceMoney;
    /*发票申请日期 */
    private Date applyDate;
    /*发票类目  0 专票  1 普票 */
    private Short invoiceCategory;
    /*发票类型  0 个人  1 企业 */
    private Short invoiceType;
    /*发票状态  0 已申请 1 已投递*/
    private Short invoiceStatus;
    /*发票抬头 */
    private String invoiceRise;
    /*识别号  */
    private BigDecimal invoiceNumber;
    /*申请人*/
    private String applyPerson;
    /*审批时间*/
    private Date approvalDate;
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

    public Short getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Short applicationType) {
        this.applicationType = applicationType;
    }

    public Long getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(Long invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Short getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(Short invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public Short getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Short invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Short getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Short invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson == null ? null : applyPerson.trim();
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
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