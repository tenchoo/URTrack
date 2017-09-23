package com.urt.po.mno;

import java.io.Serializable;
import java.util.Date;

public class LaoMnoProvideServer implements Serializable {
    private Long serverId;

    private String serverCategory;

    private String serverProtocol;

    private String serverName;

    private Date pushDate;

    private Date updateDate;

    private String serverOpen;

    private String serverDesc;

    private String handlerClass;

    private String handlerMethod;

    private String paraName1;

    private String paraName2;

    private static final long serialVersionUID = 1L;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerCategory() {
        return serverCategory;
    }

    public void setServerCategory(String serverCategory) {
        this.serverCategory = serverCategory == null ? null : serverCategory.trim();
    }

    public String getServerProtocol() {
        return serverProtocol;
    }

    public void setServerProtocol(String serverProtocol) {
        this.serverProtocol = serverProtocol == null ? null : serverProtocol.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getServerOpen() {
        return serverOpen;
    }

    public void setServerOpen(String serverOpen) {
        this.serverOpen = serverOpen == null ? null : serverOpen.trim();
    }

    public String getServerDesc() {
        return serverDesc;
    }

    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc == null ? null : serverDesc.trim();
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass == null ? null : handlerClass.trim();
    }

    public String getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(String handlerMethod) {
        this.handlerMethod = handlerMethod == null ? null : handlerMethod.trim();
    }

    public String getParaName1() {
        return paraName1;
    }

    public void setParaName1(String paraName1) {
        this.paraName1 = paraName1 == null ? null : paraName1.trim();
    }

    public String getParaName2() {
        return paraName2;
    }

    public void setParaName2(String paraName2) {
        this.paraName2 = paraName2 == null ? null : paraName2.trim();
    }
}