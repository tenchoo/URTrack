package com.urt.dto;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class TfFSsNavigationDto implements Serializable {
    private Long navigationId;

    private String url;

    private String navName;
    
    private String navPicture;

    private Long parentId;

    private String level;
    
    private List<TfFSsNavigationDto> tfFSsNavigationList = Lists.newArrayList();

    private static final long serialVersionUID = 1L;

    public Long getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Long navigationId) {
        this.navigationId = navigationId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName == null ? null : navName.trim();
    }

    public String getNavPicture() {
		return navPicture;
	}

	public void setNavPicture(String navPicture) {
		this.navPicture = navPicture;
	}

	public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

	public List<TfFSsNavigationDto> getTfFSsNavigationList() {
		return tfFSsNavigationList;
	}

	public void setTfFSsNavigationList(List<TfFSsNavigationDto> tfFSsNavigationList) {
		this.tfFSsNavigationList = tfFSsNavigationList;
	}
}