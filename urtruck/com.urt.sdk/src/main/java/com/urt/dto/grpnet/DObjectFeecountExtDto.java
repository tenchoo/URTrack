package com.urt.dto.grpnet;

import java.io.Serializable;
import java.util.List;

public class DObjectFeecountExtDto implements Serializable {
	private Integer objectId;

	private String objectName;

	private String attrType;

	private Integer attrId;

	private String remark;

	private List<GrpnetFeecountDto> grFeecountDto;


	private static final long serialVersionUID = 1L;

	
	
	public List<GrpnetFeecountDto> getGrFeecountDto() {
		return grFeecountDto;
	}

	public void setGrFeecountDto(List<GrpnetFeecountDto> grFeecountDto) {
		this.grFeecountDto = grFeecountDto;
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
