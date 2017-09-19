package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoAlmRuleLogDto implements Serializable {
	 private Long alarmId;

	    private Long cycleId;

	    private Long ruleId;

	    private Long channelCustId;

	    private Long custId;

	    private Long userId;

	    private String iccid;

	    private String dealTag;

	    private Date dealTime;

	    private String operId;

	    private String remark;

	    private String operate;

	    private String batch;

	    private Integer cardnum;

	    private String rate;

	    private String templetcontent;

	    private static final long serialVersionUID = 1L;

	    public Long getAlarmId() {
	        return alarmId;
	    }

	    public void setAlarmId(Long alarmId) {
	        this.alarmId = alarmId;
	    }

	    public Long getCycleId() {
	        return cycleId;
	    }

	    public void setCycleId(Long cycleId) {
	        this.cycleId = cycleId;
	    }

	    public Long getRuleId() {
	        return ruleId;
	    }

	    public void setRuleId(Long ruleId) {
	        this.ruleId = ruleId;
	    }

	    public Long getChannelCustId() {
	        return channelCustId;
	    }

	    public void setChannelCustId(Long channelCustId) {
	        this.channelCustId = channelCustId;
	    }

	    public Long getCustId() {
	        return custId;
	    }

	    public void setCustId(Long custId) {
	        this.custId = custId;
	    }

	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public String getIccid() {
	        return iccid;
	    }

	    public void setIccid(String iccid) {
	        this.iccid = iccid == null ? null : iccid.trim();
	    }

	    public String getDealTag() {
	        return dealTag;
	    }

	    public void setDealTag(String dealTag) {
	        this.dealTag = dealTag == null ? null : dealTag.trim();
	    }

	    public Date getDealTime() {
	        return dealTime;
	    }

	    public void setDealTime(Date dealTime) {
	        this.dealTime = dealTime;
	    }

	    public String getOperId() {
	        return operId;
	    }

	    public void setOperId(String operId) {
	        this.operId = operId == null ? null : operId.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }

	    public String getOperate() {
	        return operate;
	    }

	    public void setOperate(String operate) {
	        this.operate = operate == null ? null : operate.trim();
	    }

	    public String getBatch() {
	        return batch;
	    }

	    public void setBatch(String batch) {
	        this.batch = batch == null ? null : batch.trim();
	    }

	    public Integer getCardnum() {
	        return cardnum;
	    }

	    public void setCardnum(Integer cardnum) {
	        this.cardnum = cardnum;
	    }

	    public String getRate() {
	        return rate;
	    }

	    public void setRate(String rate) {
	        this.rate = rate == null ? null : rate.trim();
	    }

	    public String getTempletcontent() {
	        return templetcontent;
	    }

	    public void setTempletcontent(String templetcontent) {
	        this.templetcontent = templetcontent == null ? null : templetcontent.trim();
	    }
}