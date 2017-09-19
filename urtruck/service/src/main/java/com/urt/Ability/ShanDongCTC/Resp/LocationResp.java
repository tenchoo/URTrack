package com.urt.Ability.ShanDongCTC.Resp;

import antlr.collections.List;

public class LocationResp {
	
	private String MSID_TYPE;
	private String MSID;
	private List LATITUDETYPE;
	private String ROAMCITY;
	private String LONGITUDE;
	private String LOCALTIME;
	private String POSITIONRESULT;
	private String RADIUS;
	private String POSOUR;
	private String LATITUDE;
	private String AREACODE;
	private String LONGITUDETYPE;
	public String getMSID_TYPE() {
		return MSID_TYPE;
	}
	public void setMSID_TYPE(String mSID_TYPE) {
		MSID_TYPE = mSID_TYPE;
	}
	public String getMSID() {
		return MSID;
	}
	public void setMSID(String mSID) {
		MSID = mSID;
	}
	public List getLATITUDETYPE() {
		return LATITUDETYPE;
	}
	public void setLATITUDETYPE(List lATITUDETYPE) {
		LATITUDETYPE = lATITUDETYPE;
	}
	public String getROAMCITY() {
		return ROAMCITY;
	}
	public void setROAMCITY(String rOAMCITY) {
		ROAMCITY = rOAMCITY;
	}
	public String getLONGITUDE() {
		return LONGITUDE;
	}
	public void setLONGITUDE(String lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}
	public String getLOCALTIME() {
		return LOCALTIME;
	}
	public void setLOCALTIME(String lOCALTIME) {
		LOCALTIME = lOCALTIME;
	}
	public String getPOSITIONRESULT() {
		return POSITIONRESULT;
	}
	public void setPOSITIONRESULT(String pOSITIONRESULT) {
		POSITIONRESULT = pOSITIONRESULT;
	}
	public String getRADIUS() {
		return RADIUS;
	}
	public void setRADIUS(String rADIUS) {
		RADIUS = rADIUS;
	}
	public String getPOSOUR() {
		return POSOUR;
	}
	public void setPOSOUR(String pOSOUR) {
		POSOUR = pOSOUR;
	}
	public String getLATITUDE() {
		return LATITUDE;
	}
	public void setLATITUDE(String lATITUDE) {
		LATITUDE = lATITUDE;
	}
	public String getAREACODE() {
		return AREACODE;
	}
	public void setAREACODE(String aREACODE) {
		AREACODE = aREACODE;
	}
	public String getLONGITUDETYPE() {
		return LONGITUDETYPE;
	}
	public void setLONGITUDETYPE(String lONGITUDETYPE) {
		LONGITUDETYPE = lONGITUDETYPE;
	}
	public LocationResp(String mSID_TYPE, String mSID, List lATITUDETYPE,
			String rOAMCITY, String lONGITUDE, String lOCALTIME,
			String pOSITIONRESULT, String rADIUS, String pOSOUR,
			String lATITUDE, String aREACODE, String lONGITUDETYPE) {
		super();
		MSID_TYPE = mSID_TYPE;
		MSID = mSID;
		LATITUDETYPE = lATITUDETYPE;
		ROAMCITY = rOAMCITY;
		LONGITUDE = lONGITUDE;
		LOCALTIME = lOCALTIME;
		POSITIONRESULT = pOSITIONRESULT;
		RADIUS = rADIUS;
		POSOUR = pOSOUR;
		LATITUDE = lATITUDE;
		AREACODE = aREACODE;
		LONGITUDETYPE = lONGITUDETYPE;
	}
	public LocationResp() {
		super();
	}
	@Override
	public String toString() {
		return "LocationResp [MSID_TYPE=" + MSID_TYPE + ", MSID=" + MSID
				+ ", LATITUDETYPE=" + LATITUDETYPE + ", ROAMCITY=" + ROAMCITY
				+ ", LONGITUDE=" + LONGITUDE + ", LOCALTIME=" + LOCALTIME
				+ ", POSITIONRESULT=" + POSITIONRESULT + ", RADIUS=" + RADIUS
				+ ", POSOUR=" + POSOUR + ", LATITUDE=" + LATITUDE
				+ ", AREACODE=" + AREACODE + ", LONGITUDETYPE=" + LONGITUDETYPE
				+ "]";
	}
	

}
