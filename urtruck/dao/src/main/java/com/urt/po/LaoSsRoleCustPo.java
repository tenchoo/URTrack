package com.urt.po;

import java.io.Serializable;

public class LaoSsRoleCustPo implements Serializable {
    private Long roleId;

    private Long custId;

    private static final long serialVersionUID = 1L;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }
}