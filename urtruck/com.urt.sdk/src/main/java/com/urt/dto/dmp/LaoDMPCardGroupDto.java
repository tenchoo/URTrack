package com.urt.dto.dmp;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPCardGroupDto implements Serializable {
    private String imei;

    private Long groupId;

    private Date creattime;

    private Date updatetime;

    private Short flag;

    private static final long serialVersionUID = 1L;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }
}