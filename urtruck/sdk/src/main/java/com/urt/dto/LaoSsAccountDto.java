package com.urt.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class LaoSsAccountDto implements Serializable {
	
	//用户
	private Long acconutId;

    private String loginName;

    private String nickname;

    private String password;

    private String salt;

    private String status;

    private Date createDate;

    private String remark;

    private String relatedType;

    private String relatedId;

    private Long custId;
    
    private List<LaoSsRoleDto> roles;
    
    private String roleName;
    
    private String plainPassword;

    private List<LaoSsNavigationDto> navigations;

    private List<LaoSsResourceDto> resources;
    
    private String fstStruct;
    
    private String secStruct;
    
    private static final long serialVersionUID = 1L;


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Long getAcconutId() {
		return acconutId;
	}


	public void setAcconutId(Long acconutId) {
		this.acconutId = acconutId;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRelatedType() {
		return relatedType;
	}


	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}


	public String getRelatedId() {
		return relatedId;
	}


	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}


	/*public Integer getCustId() {
		return custId;
	}


	public void setCustId(Integer custId) {
		this.custId = custId;
	}*/


	public List<LaoSsRoleDto> getRoles() {
		return roles;
	}


	public Long getCustId() {
		return custId;
	}


	public void setCustId(Long custId) {
		this.custId = custId;
	}


	public void setRoles(List<LaoSsRoleDto> roles) {
		this.roles = roles;
	}


	public String getPlainPassword() {
		return plainPassword;
	}


	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}


	public List<LaoSsNavigationDto> getNavigations() {
		return navigations;
	}


	public void setNavigations(List<LaoSsNavigationDto> navigations) {
		this.navigations = navigations;
	}


	public List<LaoSsResourceDto> getResources() {
		return resources;
	}


	public void setResources(List<LaoSsResourceDto> resources) {
		this.resources = resources;
	}


	public String getFstStruct() {
		return fstStruct;
	}


	public void setFstStruct(String fstStruct) {
		this.fstStruct = fstStruct;
	}


	public String getSecStruct() {
		return secStruct;
	}


	public void setSecStruct(String secStruct) {
		this.secStruct = secStruct;
	}

    

}