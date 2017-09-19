package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoFtpfileInfo implements Serializable {
    private Long id;

    private String fileName;

    private String iccid;

    private String msisdn;

    private Date createdate;

    private String tradetypecode;

    private String errordesc;

    private Short status;

    private Short processmode;

    private Integer goodsreleaseid;

    private Short operatorsPid;

    private String custid;

    private Date updatedate;

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

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getTradetypecode() {
        return tradetypecode;
    }

    public void setTradetypecode(String tradetypecode) {
        this.tradetypecode = tradetypecode == null ? null : tradetypecode.trim();
    }

    public String getErrordesc() {
        return errordesc;
    }

    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc == null ? null : errordesc.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getProcessmode() {
        return processmode;
    }

    public void setProcessmode(Short processmode) {
        this.processmode = processmode;
    }

    public Integer getGoodsreleaseid() {
        return goodsreleaseid;
    }

    public void setGoodsreleaseid(Integer goodsreleaseid) {
        this.goodsreleaseid = goodsreleaseid;
    }

    public Short getOperatorsPid() {
        return operatorsPid;
    }

    public void setOperatorsPid(Short operatorsPid) {
        this.operatorsPid = operatorsPid;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

   
    public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }
}