package com.urt.dto;



import java.io.Serializable;
import java.util.Date;

public class LaoFtpfileCollectDto implements Serializable {
    private Long id;

    private String fileName;

    private Date createDate;

    private Date updateDate;

    private String tradetypecode;

    private Integer cardtotal;

    private Integer successnum;

    private Integer failnum;

    private Integer pendingnum;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
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

    public String getTradetypecode() {
        return tradetypecode;
    }

    public void setTradetypecode(String tradetypecode) {
        this.tradetypecode = tradetypecode == null ? null : tradetypecode.trim();
    }

    public Integer getCardtotal() {
        return cardtotal;
    }

    public void setCardtotal(Integer cardtotal) {
        this.cardtotal = cardtotal;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public Integer getFailnum() {
        return failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }

    public Integer getPendingnum() {
        return pendingnum;
    }

    public void setPendingnum(Integer pendingnum) {
        this.pendingnum = pendingnum;
    }

    public String getParaName1() {
        return paraName1;
    }

    public void setParaName1(String paraName1) {
        this.paraName1 = paraName1 == null ? null : paraName1.trim();
    }

    public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }
}