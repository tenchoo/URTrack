package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class DiscontDto implements Serializable {
    private Integer discontId;

    private String discontName;

    private Date createdate;

    private Date updatedate;

    private Date startdate;

    private Date enddate;

    private String discontType;

    private Integer discontValue;

    private static final long serialVersionUID = 1L;

    public Integer getDiscontId() {
        return discontId;
    }

    public void setDiscontId(Integer discontId) {
        this.discontId = discontId;
    }

    public String getDiscontName() {
        return discontName;
    }

    public void setDiscontName(String discontName) {
        this.discontName = discontName == null ? null : discontName.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getDiscontType() {
        return discontType;
    }

    public void setDiscontType(String discontType) {
        this.discontType = discontType == null ? null : discontType.trim();
    }

    public Integer getDiscontValue() {
        return discontValue;
    }

    public void setDiscontValue(Integer discontValue) {
        this.discontValue = discontValue;
    }
}