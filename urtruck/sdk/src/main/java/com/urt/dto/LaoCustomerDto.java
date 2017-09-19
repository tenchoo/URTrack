package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoCustomerDto implements Serializable {
	private Long custId;

    private Short partitionId;

    private String custName;

    /**
     * 0 个人用户
     * 1 企业客户 
     */
    
    private String custType;

    /**
     * 0 潜在客户
     * 1 在用客户
     * 2 注销客户
     */
    private String custState;

    private String psptTypeCode;

    private String psptId;

    private Date removeDate;

    private String removeChange;

    private String rsrvStr1;

    private Date rsrvDate1;

    private Date inDate;

    private String inStaffId;
    
    private Long parentId;
    
    private Long devCust;

    private Long devAct;
    
    //下属企业数量
    private int enterpriseNumber;
    
    public Long getDevCust() {
		return devCust;
	}

	public void setDevCust(Long devCust) {
		this.devCust = devCust;
	}

	public Long getDevAct() {
		return devAct;
	}

	public void setDevAct(Long devAct) {
		this.devAct = devAct;
	}

	private boolean admin;

    private static final long serialVersionUID = 1L;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Short getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Short partitionId) {
        this.partitionId = partitionId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType == null ? null : custType.trim();
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState == null ? null : custState.trim();
    }

    public String getPsptTypeCode() {
        return psptTypeCode;
    }

    public void setPsptTypeCode(String psptTypeCode) {
        this.psptTypeCode = psptTypeCode == null ? null : psptTypeCode.trim();
    }

    public String getPsptId() {
        return psptId;
    }

    public void setPsptId(String psptId) {
        this.psptId = psptId == null ? null : psptId.trim();
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }

    public String getRemoveChange() {
        return removeChange;
    }

    public void setRemoveChange(String removeChange) {
        this.removeChange = removeChange == null ? null : removeChange.trim();
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1 == null ? null : rsrvStr1.trim();
    }

    public Date getRsrvDate1() {
        return rsrvDate1;
    }

    public void setRsrvDate1(Date rsrvDate1) {
        this.rsrvDate1 = rsrvDate1;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getInStaffId() {
        return inStaffId;
    }

    public void setInStaffId(String inStaffId) {
        this.inStaffId = inStaffId == null ? null : inStaffId.trim();
    }

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

    public int getEnterpriseNumber() {
		return enterpriseNumber;
	}

	public void setEnterpriseNumber(int enterpriseNumber) {
		this.enterpriseNumber = enterpriseNumber;
	}

	@Override
	public String toString() {
		return "LaoCustomerDto [custId=" + custId + ", partitionId=" + partitionId + ", custName=" + custName
				+ ", custType=" + custType + ", custState=" + custState + ", psptTypeCode=" + psptTypeCode + ", psptId="
				+ psptId + ", removeDate=" + removeDate + ", removeChange=" + removeChange + ", rsrvStr1=" + rsrvStr1
				+ ", rsrvDate1=" + rsrvDate1 + ", inDate=" + inDate + ", inStaffId=" + inStaffId + ", parentId="
				+ parentId + ", devCust=" + devCust + ", devAct=" + devAct + ", admin=" + admin + ", enterpriseNumber=" + enterpriseNumber +"]";
	}
    
    
}
