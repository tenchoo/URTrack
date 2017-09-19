package com.urt.Ability.unicom.util;

/**
 * 通信计划
 * @author liumh8
 *
 */
public enum NetworkAccessConfigEnum {
	
	/**	专用通道	*/
	APN1("107918","bjlenovo01.xfdz.njm2mapn","110WLW004085_NJ_DATA_4G_SP"),
	/**	通用通道	*/
//	APN2("99318","UNIM2M.NJM2MAPN","110WLW004085_NJ_DATA_4G"),
	APN2("99318","bjlenovo02.xfdz.njm2mapn","110WLW004085_NJ_DATA_4G"),
	/**	专用通道+通用通道	*/
	APN_UNION("106818","bjlenovo01.xfdz.njm2mapn+UNIM2M.NJM2MAPN","110WLW004085_NJ_DATA_APNmerged");
	
	private String nacId;
	private String apn;
	private String name;
	
	private NetworkAccessConfigEnum(String nacId,String apn,String name){
		this.nacId = nacId;
		this.apn = apn;
		this.name = name;
	}

	public String getNacId() {
		return nacId;
	}

	public void setNacId(String nacId) {
		this.nacId = nacId;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
