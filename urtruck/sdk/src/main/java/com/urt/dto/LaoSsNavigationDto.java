package com.urt.dto;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class LaoSsNavigationDto implements Serializable {
	
	private Long navigationId;

    private String url;

    private String navName;

    private String navPicture;

    private String urlLevel;

    private Long parentId;

    private Integer showIndex;
    
    private List<LaoSsNavigationDto> navigationList = Lists.newArrayList();

    private static final long serialVersionUID = 1L;


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


	public String getUrlLevel() {
		return urlLevel;
	}

	public void setUrlLevel(String urlLevel) {
		this.urlLevel = urlLevel;
	}

	public List<LaoSsNavigationDto> getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(List<LaoSsNavigationDto> navigationList) {
		this.navigationList = navigationList;
	}

	public Long getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(Long navigationId) {
		this.navigationId = navigationId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	

}