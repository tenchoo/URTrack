package com.urt.dto.Remain;

import java.io.Serializable;

public class BalAlarmDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long cal;

    private Long balRemain;

    private String ruleName;
    
    private String email;
    
    private String telphone;

    private Long chanCustId;
    
    
    public BalAlarmDto() {
    }

    public BalAlarmDto(Long cal, Long balRemain, String ruleName, String email, String telphone, Long chanCustId) {
        this.cal = cal;
        this.balRemain = balRemain;
        this.ruleName = ruleName;
        this.email = email;
        this.telphone = telphone;
        this.chanCustId = chanCustId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Long getChanCustId() {
        return chanCustId;
    }

    public void setChanCustId(Long chanCustId) {
        this.chanCustId = chanCustId;
    }

    public Long getCal() {
        return cal;
    }

    public void setCal(Long cal) {
        this.cal = cal;
    }

    public Long getBalRemain() {
        return balRemain;
    }

    public void setBalRemain(Long balRemain) {
        this.balRemain = balRemain;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    
    
}
