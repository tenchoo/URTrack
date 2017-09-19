package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class LocationQRRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long MSID_TYPE;			//0：定位成功 130：定位失败，用户关机 102：定位失败，用户位置 99：定位失败 -2：sign参数值错误 -3：用户名或者密码错误
	private String MSID;			//定位码号
	private String ROAMCITY	;		//定位漫游城市
	private String LONGITUDE;		//有经度时返回该值
	private String LOCALTIME;		//格式YYYYMMDDHHMMSS
	private String POSITIONRESULT;	//定位结果值
	private String RADIUS;			//位置信息返回的扇区半径信息
	private String POSOUR;			//定义位置来源
	private String LATITUDE	;		//有纬度时返回该值
	private String AREACODE	;		//用于表示返回经纬度所对应的GIS的区号
	public Long getMSID_TYPE() {
		return MSID_TYPE;
	}
	public void setMSID_TYPE(Long mSID_TYPE) {
		MSID_TYPE = mSID_TYPE;
	}
	public String getMSID() {
		return MSID;
	}
	public void setMSID(String mSID) {
		MSID = mSID;
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

	
}
