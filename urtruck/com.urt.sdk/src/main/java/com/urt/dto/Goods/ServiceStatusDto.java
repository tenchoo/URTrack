package com.urt.dto.Goods;

import java.io.Serializable;
import java.util.Date;

public class ServiceStatusDto implements Serializable{
	 private String stateCode;

	    private Integer serviceId;

	    private String serviceName;

	    private Date createdate;

	    private Date updatedate;

	    private Date startdate;

	    private Date enddate;

	    private Integer operatorsId;
	    
	    private String statechangeId;

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

		/**
		 * @return the statechangeId
		 */
		public String getStatechangeId() {
			return statechangeId;
		}

		/**
		 * @param statechangeId the statechangeId to set
		 */
		public void setStatechangeId(String statechangeId) {
			this.statechangeId = statechangeId;
		}
	    
}
