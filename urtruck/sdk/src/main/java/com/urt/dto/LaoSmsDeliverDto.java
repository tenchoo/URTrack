package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoSmsDeliverDto implements Serializable {
    private Long deliverId;

    private String srcNumber;

    private String destNumber;

    private String smsContent;

    private Date pushDate;

    private Long smsId;

    private Long pushState;

    private String pushDesc;

    private static final long serialVersionUID = 1L;

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public String getSrcNumber() {
        return srcNumber;
    }

    public void setSrcNumber(String srcNumber) {
        this.srcNumber = srcNumber == null ? null : srcNumber.trim();
    }

    public String getDestNumber() {
        return destNumber;
    }

    public void setDestNumber(String destNumber) {
        this.destNumber = destNumber == null ? null : destNumber.trim();
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getPushState() {
        return pushState;
    }

    public void setPushState(Long pushState) {
        this.pushState = pushState;
    }

    public String getPushDesc() {
        return pushDesc;
    }

    public void setPushDesc(String pushDesc) {
        this.pushDesc = pushDesc == null ? null : pushDesc.trim();
    }
}