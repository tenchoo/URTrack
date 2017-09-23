package com.urt.po;

import java.io.Serializable;

public class ServiceStatusChg implements Serializable {
	
	//主键
    private Short id;

    //业务类型：100、110激活，120产品套餐变更，130 停机， 140 开机
    private String tradeTypeCode;

    //旧的状态
    private String oldStateCode;

    //新状态
    private String newStateCode;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode == null ? null : tradeTypeCode.trim();
    }

    public String getOldStateCode() {
        return oldStateCode;
    }

    public void setOldStateCode(String oldStateCode) {
        this.oldStateCode = oldStateCode == null ? null : oldStateCode.trim();
    }

    public String getNewStateCode() {
        return newStateCode;
    }

    public void setNewStateCode(String newStateCode) {
        this.newStateCode = newStateCode == null ? null : newStateCode.trim();
    }
}