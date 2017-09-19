package com.urt.po;

import java.io.Serializable;
import java.util.List;

public class DObjectFeecountExt implements Serializable {
    private Integer objectId;

    private String objectName;

    private String attrType;

    private Integer attrId;

    private String remark;
    
    private List<GrpnetFeecount> grpnetFeecount;

    private static final long serialVersionUID = 1L;
     

	public List<GrpnetFeecount> getGrpnetFeecount() {
		return grpnetFeecount;
	}

	public void setGrpnetFeecount(List<GrpnetFeecount> grpnetFeecount) {
		this.grpnetFeecount = grpnetFeecount;
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

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType == null ? null : attrType.trim();
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}