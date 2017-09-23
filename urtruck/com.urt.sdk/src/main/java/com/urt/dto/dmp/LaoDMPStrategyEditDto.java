package com.urt.dto.dmp;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPStrategyEditDto implements Serializable {
    private Long id;

    private Long channelcustId;

    private String targittype;

    private String schemeName;

    private String schemeComment;

    private String delflag;

    private Date createtime;

    private Date updatetime;
    
    private String groupIds;
    
    private String strategyJson; 

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelcustId() {
        return channelcustId;
    }

    public void setChannelcustId(Long channelcustId) {
        this.channelcustId = channelcustId;
    }

    public String getTargittype() {
        return targittype;
    }

    public void setTargittype(String targittype) {
        this.targittype = targittype == null ? null : targittype.trim();
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName == null ? null : schemeName.trim();
    }

    public String getSchemeComment() {
        return schemeComment;
    }

    public void setSchemeComment(String schemeComment) {
        this.schemeComment = schemeComment == null ? null : schemeComment.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}


	public String getStrategyJson() {
		return strategyJson;
	}

	public void setStrategyJson(String strategyJson) {
		this.strategyJson = strategyJson;
	}

	@Override
	public String toString() {
		return "LaoDMPStrategyEditDto [id=" + id + ", channelcustId="
				+ channelcustId + ", targittype=" + targittype
				+ ", schemeName=" + schemeName + ", schemeComment="
				+ schemeComment + ", delflag=" + delflag + ", createtime="
				+ createtime + ", updatetime=" + updatetime + ", groupIds="
				+ groupIds + ", strategyJson=" + strategyJson + "]";
	}

}