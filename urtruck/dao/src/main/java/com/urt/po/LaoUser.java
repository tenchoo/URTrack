package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUser implements Serializable {
	
	//用户id
    private Long userId;

    //企业客户id
    private Long channelCustId;
    
    //企业客户名称
    private String channelCustName;

    //个人客户id
    private Long custId;
    
    //个人客户名称
    private String custName;

    //服务号码
    private String msisdn;

    //运营商id
    private Integer operatorsId;
    
    //运营商名称
    private String operatorsName;
    
    //暂无用
    private Long scoreValue;

    //暂无用
    private Integer creditClass;

    //暂无用
    private Long basicCreditValue;

    //暂无用
    private Long creditValue;

    //是否开始计费（测试期和待激活库存，值为0,使用值为1）
    private String acctTag;

    //预付费或后付费
    private String prepayTag;

    //入库时间
    private Date inDate;

    //第一次划拨给客户时间
    private Date openDate;

    //是否销户
    private String removeTag;

    //销户时间
    private Date destroyTime;

    //预销户时间，暂不用，可以用来存到期时间
    private Date preDestroyTime;

    //激活时间
    private Date firstCallTime;

    //最后一次停机时间
    private Date lastStopTime;

    //iccid
    private String iccid;

    //暂无用
    private String deviceId;

    //暂无用
    private String statusCode;

    //暂无用
    private Short aliveCheckTime;

    //imei号
    private String imei;

    //卡状态(对应Lao_service_status表)
    private String stateCode;

    //行业分类一级(如：车联网)
    private String attribute1;

    //行业分类二级(如：共享单车)
    private String attribute2;

    //客户的一级分类(如：智能锁厂房)
    private String value1;

    //客户的二级分类(如：批次号F)
    private String value2;
    
    //主服务名称->暂无用
    private String serviceName;
    
    //行业分类一级value
    private String attr1Name;
    
    //行业行业分类二级value
    private String attr2Name;
    
    //客户的一级分类value
    private String v1Name;
    
    //客户的二级分类value
    private String v2Name;
    
    //测试期开始日期
    private Date testStartDate;

    //测试周期
    private Short testCycle;

    //
    private String vic;

    //imsi(类似于iccid的短号码)
    private String imsi;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelCustId() {
        return channelCustId;
    }

    public void setChannelCustId(Long channelCustId) {
        this.channelCustId = channelCustId;
    }

    public String getChannelCustName() {
		return channelCustName;
	}

	public void setChannelCustName(String channelCustName) {
		this.channelCustName = channelCustName;
	}

	public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }
    
    public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	public Long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Long scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Integer getCreditClass() {
        return creditClass;
    }

    public void setCreditClass(Integer creditClass) {
        this.creditClass = creditClass;
    }

    public Long getBasicCreditValue() {
        return basicCreditValue;
    }

    public void setBasicCreditValue(Long basicCreditValue) {
        this.basicCreditValue = basicCreditValue;
    }

    public Long getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Long creditValue) {
        this.creditValue = creditValue;
    }

    public String getAcctTag() {
        return acctTag;
    }

    public void setAcctTag(String acctTag) {
        this.acctTag = acctTag == null ? null : acctTag.trim();
    }

    public String getPrepayTag() {
        return prepayTag;
    }

    public void setPrepayTag(String prepayTag) {
        this.prepayTag = prepayTag == null ? null : prepayTag.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getRemoveTag() {
        return removeTag;
    }

    public void setRemoveTag(String removeTag) {
        this.removeTag = removeTag == null ? null : removeTag.trim();
    }

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public Date getPreDestroyTime() {
        return preDestroyTime;
    }

    public void setPreDestroyTime(Date preDestroyTime) {
        this.preDestroyTime = preDestroyTime;
    }

    public Date getFirstCallTime() {
        return firstCallTime;
    }

    public void setFirstCallTime(Date firstCallTime) {
        this.firstCallTime = firstCallTime;
    }

    public Date getLastStopTime() {
        return lastStopTime;
    }

    public void setLastStopTime(Date lastStopTime) {
        this.lastStopTime = lastStopTime;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public Short getAliveCheckTime() {
        return aliveCheckTime;
    }

    public void setAliveCheckTime(Short aliveCheckTime) {
        this.aliveCheckTime = aliveCheckTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1 == null ? null : attribute1.trim();
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1 == null ? null : value1.trim();
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2 == null ? null : attribute2.trim();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2 == null ? null : value2.trim();
    }

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAttr1Name() {
		return attr1Name;
	}

	public void setAttr1Name(String attr1Name) {
		this.attr1Name = attr1Name;
	}

	public String getAttr2Name() {
		return attr2Name;
	}

	public void setAttr2Name(String attr2Name) {
		this.attr2Name = attr2Name;
	}

	public String getV1Name() {
		return v1Name;
	}

	public void setV1Name(String v1Name) {
		this.v1Name = v1Name;
	}

	public String getV2Name() {
		return v2Name;
	}

	public void setV2Name(String v2Name) {
		this.v2Name = v2Name;
	}

	public Date getTestStartDate() {
		return testStartDate;
	}

	public void setTestStartDate(Date testStartDate) {
		this.testStartDate = testStartDate;
	}

	public Short getTestCycle() {
		return testCycle;
	}

	public void setTestCycle(Short testCycle) {
		this.testCycle = testCycle;
	}

	public String getVic() {
		return vic;
	}

	public void setVic(String vic) {
		this.vic = vic;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
    
}