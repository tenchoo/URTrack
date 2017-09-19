package com.urt.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class TfFSsUserDto implements Serializable {
    private Long userId;

    private String loginName;

    private String nickname;

    private String plainPassword;
    
    private String password;
    
    private String salt;

    private String email;

    private String phone;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    private Date createDate;

    private String remark;
    
    //一个用户对应一个或多个角色
    private List<TfFSsRoleDto> roles;
    
    private List<TfFSsNavigationDto> tfFSsNavigationList;
    
    private List<TfFSsResourceDto> tfFSsResourceList;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

	
	public List<TfFSsRoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<TfFSsRoleDto> roles) {
		this.roles = roles;
	}
	
	public List<TfFSsNavigationDto> getTfFSsNavigationList() {
		return tfFSsNavigationList;
	}

	public void setTfFSsNavigationList(List<TfFSsNavigationDto> tfFSsNavigationList) {
		this.tfFSsNavigationList = tfFSsNavigationList;
	}

	public List<TfFSsResourceDto> getTfFSsResourceList() {
		return tfFSsResourceList;
	}

	public void setTfFSsResourceList(List<TfFSsResourceDto> tfFSsResourceList) {
		this.tfFSsResourceList = tfFSsResourceList;
	}
}