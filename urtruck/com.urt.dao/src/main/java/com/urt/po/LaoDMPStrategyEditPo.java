package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPStrategyEditPo implements Serializable {
    private Long id;

    private Long channelcustId;

    private String targittype;

    private String schemeName;

    private String schemeComment;

    private String delflag;

    private Date createtime;

    private Date updatetime;

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
}