package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoBalAlmLogPo implements Serializable {
    private Long alarmId;

    private String ruleName;

    private Long channelCustId;

    private Long curBal;

    private Long remianbal;

    private String smsTag;

    private String emailTag;

    private Date dealTime;

    private static final long serialVersionUID = 1L;

    public Long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Long alarmId) {
        this.alarmId = alarmId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public Long getCurBal() {
        return curBal;
    }

    public void setCurBal(Long curBal) {
        this.curBal = curBal;
    }

    public Long getRemianbal() {
        return remianbal;
    }

    public void setRemianbal(Long remianbal) {
        this.remianbal = remianbal;
    }

    public String getSmsTag() {
        return smsTag;
    }

    public void setSmsTag(String smsTag) {
        this.smsTag = smsTag == null ? null : smsTag.trim();
    }

    public String getEmailTag() {
        return emailTag;
    }

    public void setEmailTag(String emailTag) {
        this.emailTag = emailTag == null ? null : emailTag.trim();
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
}