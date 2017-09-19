package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class OperatorsDto implements Serializable {
    private Integer operatorsId;

    private String operatorsName;

    private Integer operatorsP;

    private Date updatedate;

    private Date createdate;
    
    private String async;

    private static final long serialVersionUID = 1L;

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getOperatorsName() {
        return operatorsName;
    }

    public void setOperatorsName(String operatorsName) {
        this.operatorsName = operatorsName == null ? null : operatorsName.trim();
    }

    public Integer getOperatorsP() {
        return operatorsP;
    }

    public void setOperatorsP(Integer operatorsP) {
        this.operatorsP = operatorsP;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

	public String getAsync() {
		return async;
	}

	public void setAsync(String async) {
		this.async = async;
	}
    
    
}