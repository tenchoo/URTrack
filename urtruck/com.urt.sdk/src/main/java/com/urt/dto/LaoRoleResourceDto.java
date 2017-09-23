package com.urt.dto;

import java.io.Serializable;

public class LaoRoleResourceDto implements Serializable {
    private Long roleId;

    private Long resourceId;

    private static final long serialVersionUID = 1L;

    
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}