package com.urt.dto;

import java.io.Serializable;
import java.util.Date;

public class LaoCustGroupDto implements Serializable{
	private Long custId;

    private Short partitionId;

    private String groupId;

    private String custName;

    private String groupType;

    private String custClassType;

    private String groupStatus;

    private String groupAddr;

    private String provinceCode;

    private String eparchyCode;

    private String unifyPayCode;

    private String orgStructCode;

    private String busiLicenceType;

    private String busiLicenceNo;

    private Date busiLicenceValidDate;

    private String groupMemo;

    private String international;

    private String juristicPsptId;

    private String juristicPsptType;

    private String busiTaxId;

    private String apiKey;
    
    private String sellType;
    
    private String countrySeat;

    private String regionCode;
    
    public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getCountrySeat() {
		return countrySeat;
	}

	public void setCountrySeat(String countrySeat) {
		this.countrySeat = countrySeat;
	}

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType == null ? null : groupType.trim();
    }

    public String getCustClassType() {
        return custClassType;
    }

    public void setCustClassType(String custClassType) {
        this.custClassType = custClassType == null ? null : custClassType.trim();
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus == null ? null : groupStatus.trim();
    }

    public String getGroupAddr() {
        return groupAddr;
    }

    public void setGroupAddr(String groupAddr) {
        this.groupAddr = groupAddr == null ? null : groupAddr.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode == null ? null : eparchyCode.trim();
    }

    public String getUnifyPayCode() {
        return unifyPayCode;
    }

    public void setUnifyPayCode(String unifyPayCode) {
        this.unifyPayCode = unifyPayCode == null ? null : unifyPayCode.trim();
    }

    public String getOrgStructCode() {
        return orgStructCode;
    }

    public void setOrgStructCode(String orgStructCode) {
        this.orgStructCode = orgStructCode == null ? null : orgStructCode.trim();
    }

    public String getBusiLicenceType() {
        return busiLicenceType;
    }

    public void setBusiLicenceType(String busiLicenceType) {
        this.busiLicenceType = busiLicenceType == null ? null : busiLicenceType.trim();
    }

    public String getBusiLicenceNo() {
        return busiLicenceNo;
    }

    public void setBusiLicenceNo(String busiLicenceNo) {
        this.busiLicenceNo = busiLicenceNo == null ? null : busiLicenceNo.trim();
    }

    public Date getBusiLicenceValidDate() {
        return busiLicenceValidDate;
    }

    public void setBusiLicenceValidDate(Date busiLicenceValidDate) {
        this.busiLicenceValidDate = busiLicenceValidDate;
    }

    public String getGroupMemo() {
        return groupMemo;
    }

    public void setGroupMemo(String groupMemo) {
        this.groupMemo = groupMemo == null ? null : groupMemo.trim();
    }

    public String getInternational() {
        return international;
    }

    public void setInternational(String international) {
        this.international = international == null ? null : international.trim();
    }

    public String getJuristicPsptId() {
        return juristicPsptId;
    }

    public void setJuristicPsptId(String juristicPsptId) {
        this.juristicPsptId = juristicPsptId == null ? null : juristicPsptId.trim();
    }

    public String getJuristicPsptType() {
        return juristicPsptType;
    }

    public void setJuristicPsptType(String juristicPsptType) {
        this.juristicPsptType = juristicPsptType == null ? null : juristicPsptType.trim();
    }

    public String getBusiTaxId() {
        return busiTaxId;
    }

    public void setBusiTaxId(String busiTaxId) {
        this.busiTaxId = busiTaxId == null ? null : busiTaxId.trim();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey == null ? null : apiKey.trim();
    }

	public String getSellType() {
		return sellType;
	}

	public void setSellType(String sellType) {
		this.sellType = sellType;
	}
    
    
}
