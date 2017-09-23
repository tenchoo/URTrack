package com.urt.po;

import java.io.Serializable;
import java.util.List;

public class DObjectFeediscntExt implements Serializable {
    private Integer attrId;

    private String attrType;

    private Integer objectId;

    private String objectName;

    private String remark;

    private List<GrpnetFeediscnt> grpnetFeediscnt;
    
    private static final long serialVersionUID = 1L;
    

	public List<GrpnetFeediscnt> getGrpnetFeediscnt() {
		return grpnetFeediscnt;
	}

	public void setGrpnetFeediscnt(List<GrpnetFeediscnt> grpnetFeediscnt) {
		this.grpnetFeediscnt = grpnetFeediscnt;
	}

	public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType == null ? null : attrType.trim();
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}