package com.urt.dto;

import java.io.Serializable;

public class TfFSsRoleNavigationDto implements Serializable {
    private Long roleId;

    private Long navigationId;

    private static final long serialVersionUID = 1L;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Long navigationId) {
        this.navigationId = navigationId;
    }
}