package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoProvideServerDto implements Serializable {
    private Long serverId;

    private String serverName;

    private String serverTag;

    private String operationTag;

    private Date publishDate;

    private String serverOpen;

    private String serverDesc;

    private String paraName1;

    private String paraName2;
    
    private Boolean check;

    public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	private static final long serialVersionUID = 1L;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerTag() {
        return serverTag;
    }

    public void setServerTag(String serverTag) {
        this.serverTag = serverTag == null ? null : serverTag.trim();
    }

    public String getOperationTag() {
        return operationTag;
    }

    public void setOperationTag(String operationTag) {
        this.operationTag = operationTag == null ? null : operationTag.trim();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
