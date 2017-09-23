package com.urt.dto.grpnet;

import java.io.Serializable;

public class UserDiscntList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Date;
	private String ProductId;
	private String ProductName;
	private String DiscntTypeCode;
	private String DiscntTypeName;
	private String TotalDiscnt;
	private String UsedDiscnt;
	private String Excess;
	private String Remain;
	private String DiscntId;
	private String DiscntName;
	private String UnitDec;
	private String DiscntUnit;
	
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getDiscntTypeCode() {
		return DiscntTypeCode;
	}
	public void setDiscntTypeCode(String discntTypeCode) {
		DiscntTypeCode = discntTypeCode;
	}
	public String getDiscntTypeName() {
		return DiscntTypeName;
	}
	public void setDiscntTypeName(String discntTypeName) {
		DiscntTypeName = discntTypeName;
	}
	public String getTotalDiscnt() {
		return TotalDiscnt;
	}
	public void setTotalDiscnt(String totalDiscnt) {
		TotalDiscnt = totalDiscnt;
	}
	public String getUsedDiscnt() {
		return UsedDiscnt;
	}
	public void setUsedDiscnt(String usedDiscnt) {
		UsedDiscnt = usedDiscnt;
	}
	public String getExcess() {
		return Excess;
	}
	public void setExcess(String excess) {
		Excess = excess;
	}
	public String getRemain() {
		return Remain;
	}
	public void setRemain(String remain) {
		Remain = remain;
	}
	public String getDiscntId() {
		return DiscntId;
	}
	public void setDiscntId(String discntId) {
		DiscntId = discntId;
	}
	public String getDiscntName() {
		return DiscntName;
	}
	public void setDiscntName(String discntName) {
		DiscntName = discntName;
	}
	public String getUnitDec() {
		return UnitDec;
	}
	public void setUnitDec(String unitDec) {
		UnitDec = unitDec;
	}
	public String getDiscntUnit() {
		return DiscntUnit;
	}
	public void setDiscntUnit(String discntUnit) {
		DiscntUnit = discntUnit;
	}
	
}
