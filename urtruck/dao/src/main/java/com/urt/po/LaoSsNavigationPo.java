package com.urt.po;

import java.io.Serializable;

public class LaoSsNavigationPo implements Serializable {
    private Long navigationId;

    private String url;

    private String navName;

    private String navPicture;

    private String urlLevel;

    private Long parentId;

    private Short showIndex;

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
        this.navPicture = navPicture == null ? null : navPicture.trim();
    }

    public String getUrlLevel() {
        return urlLevel;
    }

    public void setUrlLevel(String urlLevel) {
        this.urlLevel = urlLevel == null ? null : urlLevel.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Short getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Short showIndex) {
        this.showIndex = showIndex;
    }
}