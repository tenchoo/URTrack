package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class IccidLib implements Serializable {
	
	//主键ID
    private Long id;

    //iccid
    private String iccid;

    //使用设备类型 NB(Notebook); MB (Mobile);  MIFI
    private String devicetype;

    //MIFI设备上的密钥
    private String privatekey;

    //卡的细分
    private String cardtype;

    //初始产品
    private String initproduct;

    //卡状态
    private String cardstatus;

    //运营商
    private String operators;

    //卡状态(测试还是正式生产环境）
    private String ctype;

    //属性一
    private String attribute1;

    //值一
    private String value1;

    //属性二
    private String attribute2;

    //值二
    private String value2;

    //0:正常  1:在维
    private String ifMaintenance;

    //用户编号
    private String custid;
    
    //移动的电话号码  服务号码
    private String msisdn;
    
    //0:成功激活1失败
    private String actived;

    //录入时间
    private Date inDate;
    
    //批次号
    private String batchId;
        
    private short testCycle;
    
    private String vic;
    
    private String imsi;
    
    private String buyOrderNo;
    
    private Long testGoodsRlsId;
    
	private String cycleState;

    public String getCycleState() {
		return cycleState;
	}

	public void setCycleState(String cycleState) {
		this.cycleState = cycleState;
	}

	public Long getTestGoodsRlsId() {
		return testGoodsRlsId;
	}

	public void setTestGoodsRlsId(Long testGoodsRlsId) {
		this.testGoodsRlsId = testGoodsRlsId;
	}


    
    public short getTestCycle() {
		return testCycle;
	}

	public void setTestCycle(short testCycle) {
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

	public String getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(String buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	//为了前台展示添加的额外字段
    private String custName;
    private String operatorsName;
    private String goodName;
    private String att1;
    private String val1;
    private String att2;
    private String val2;
    private String serviceName;
    
    private static final long serialVersionUID = 1L;



	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey == null ? null : privatekey.trim();
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getInitproduct() {
        return initproduct;
    }

    public void setInitproduct(String initproduct) {
        this.initproduct = initproduct == null ? null : initproduct.trim();
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus == null ? null : cardstatus.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? null : operators.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
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

    public String getIfMaintenance() {
        return ifMaintenance;
    }

    public void setIfMaintenance(String ifMaintenance) {
        this.ifMaintenance = ifMaintenance == null ? null : ifMaintenance.trim();
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

	/**
	 * @return the inDate
	 */
	public Date getInDate() {
		return inDate;
	}

	/**
	 * @param inDate the inDate to set
	 */
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the operatorsName
	 */
	public String getOperatorsName() {
		return operatorsName;
	}

	/**
	 * @param operatorsName the operatorsName to set
	 */
	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}

	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	/**
	 * @return the att1
	 */
	public String getAtt1() {
		return att1;
	}

	/**
	 * @param att1 the att1 to set
	 */
	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	/**
	 * @return the val1
	 */
	public String getVal1() {
		return val1;
	}

	/**
	 * @param val1 the val1 to set
	 */
	public void setVal1(String val1) {
		this.val1 = val1;
	}

	/**
	 * @return the att2
	 */
	public String getAtt2() {
		return att2;
	}

	/**
	 * @param att2 the att2 to set
	 */
	public void setAtt2(String att2) {
		this.att2 = att2;
	}

	/**
	 * @return the val2
	 */
	public String getVal2() {
		return val2;
	}

	/**
	 * @param val2 the val2 to set
	 */
	public void setVal2(String val2) {
		this.val2 = val2;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the actived
	 */
	public String getActived() {
		return actived;
	}

	/**
	 * @param actived the actived to set
	 */
	public void setActived(String actived) {
		this.actived = actived;
	}
    
}