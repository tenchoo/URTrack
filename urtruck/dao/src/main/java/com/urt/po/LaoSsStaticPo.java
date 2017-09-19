package com.urt.po;

import java.io.Serializable;

/**
 * 静态字典表
 * @author liuxl
 *
 */
public class LaoSsStaticPo implements Serializable {
	
	//主键
    private Integer staticId;

    //业务编码
    private String staticCode;

    //业务名称
    private String staticName;

    //目标表明
    private String tabName;

    //目标字段名
    private String colName;

    //企业id号
    private String custId;

    //上级主键id号
    private Long pid;

    private static final long serialVersionUID = 1L;

    public Integer getStaticId() {
        return staticId;
    }

    public void setStaticId(Integer staticId) {
        this.staticId = staticId;
    }

    public String getStaticCode() {
        return staticCode;
    }

    public void setStaticCode(String staticCode) {
        this.staticCode = staticCode == null ? null : staticCode.trim();
    }

    public String getStaticName() {
        return staticName;
    }

    public void setStaticName(String staticName) {
        this.staticName = staticName == null ? null : staticName.trim();
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName == null ? null : tabName.trim();
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName == null ? null : colName.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

   /* public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }*/
    
}