package com.urt.dto;

import java.io.Serializable;
import java.util.List;

public class UtilDto implements Serializable{
	public List<String> elementIds;

	public List<String> getElementIds() {
		return elementIds;
	}

	public void setElementIds(List<String> elementIds) {
		this.elementIds = elementIds;
	}
	
}
