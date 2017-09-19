package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDownloadfileConfig implements Serializable {
    private Long id;

    private String fileprefixName;

    private Date filesuffixDate;

    private Short isdownload;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileprefixName() {
        return fileprefixName;
    }

    public void setFileprefixName(String fileprefixName) {
        this.fileprefixName = fileprefixName == null ? null : fileprefixName.trim();
    }

    public Date getFilesuffixDate() {
        return filesuffixDate;
    }

    public void setFilesuffixDate(Date filesuffixDate) {
        this.filesuffixDate = filesuffixDate;
    }

    public Short getIsdownload() {
        return isdownload;
    }

    public void setIsdownload(Short isdownload) {
        this.isdownload = isdownload;
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