package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPGroupPo implements Serializable {
    private Long id;

    private String groupName;

    private String groupComment;

    private Long channelCustid;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupComment() {
        return groupComment;
    }

    public void setGroupComment(String groupComment) {
        this.groupComment = groupComment == null ? null : groupComment.trim();
    }

    public Long getChannelCustid() {
        return channelCustid;
    }

    public void setChannelCustid(Long channelCustid) {
        this.channelCustid = channelCustid;
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