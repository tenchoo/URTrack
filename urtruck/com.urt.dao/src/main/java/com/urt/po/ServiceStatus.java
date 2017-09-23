package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class ServiceStatus implements Serializable {
	
	//服务状态Id
    private String stateCode;
	
    //服务id
    private Integer serviceId;

    //服务状态名称
    private String serviceName;

    //创建时间
    private Date createdate;

    //更新时间
    private Date updatedate;

    //开始生效时间
    private Date startdate;

    //失效时间
    private Date enddate;

    //套餐提供商
    private Integer operatorsId;

    //状态服务id
    private String statechangeId;

    //生命周期：1、正常；2、停机；3、带激活；4、销户；5、测试期
    private String outsidestate;

    private static final long serialVersionUID = 1L;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getOperatorsId() {
        return operatorsId;
    }

    public void setOperatorsId(Integer operatorsId) {
        this.operatorsId = operatorsId;
    }

    public String getStatechangeId() {
        return statechangeId;
    }

    public void setStatechangeId(String statechangeId) {
        this.statechangeId = statechangeId == null ? null : statechangeId.trim();
    }

    public String getOutsidestate() {
        return outsidestate;
    }

    public void setOutsidestate(String outsidestate) {
        this.outsidestate = outsidestate == null ? null : outsidestate.trim();
    }
}