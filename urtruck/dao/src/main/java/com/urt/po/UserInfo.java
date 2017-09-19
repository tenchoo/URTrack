package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserInfo implements Serializable {
	private BigDecimal id;

	private String iccid;

	private String msisdn;

	private String imsi;

	private String deviceid;

	private String userid;

	private String username;

	private int userstatus;

	private String apntype;

	private Date createdate;

	private Date modifydate;

	private long alivechecktime;

	private String imei;

	private String realname;

	private String idnum;

	private Integer firstcharge;

	private int donateflag;

	private static final long serialVersionUID = 1L;

	public static enum UserInfoStatus {
		INIT(0, "初始化"), ACTIVE(1, "已激活"), DISABLED(2, "禁用"), ACTIVE_FAIL(3,
				"激活失败");

		private final int code;
		private final String message;

		private UserInfoStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCodeValue() {
			return code;
		}

		private String getMessage() {
			return message;
		}
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid == null ? null : iccid.trim();
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn == null ? null : msisdn.trim();
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi == null ? null : imsi.trim();
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid == null ? null : deviceid.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getApntype() {
		return apntype;
	}

	public void setApntype(String apntype) {
		this.apntype = apntype == null ? null : apntype.trim();
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei == null ? null : imei.trim();
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname == null ? null : realname.trim();
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum == null ? null : idnum.trim();
	}

	public Integer getFirstcharge() {
		return firstcharge;
	}

	public void setFirstcharge(Integer firstcharge) {
		this.firstcharge = firstcharge;
	}

	public int getDonateflag() {
		return donateflag;
	}

	public void setDonateflag(int donateflag) {
		this.donateflag = donateflag;
	}

	public int getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}

	public long getAlivechecktime() {
		return alivechecktime;
	}

	public void setAlivechecktime(long alivechecktime) {
		this.alivechecktime = alivechecktime;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", iccid=" + iccid + ", msisdn=" + msisdn
				+ ", imsi=" + imsi + ", deviceid=" + deviceid + ", userid="
				+ userid + ", username=" + username + ", userstatus="
				+ userstatus + ", apntype=" + apntype + ", createdate="
				+ createdate + ", modifydate=" + modifydate
				+ ", alivechecktime=" + alivechecktime + ", imei=" + imei
				+ ", realname=" + realname + ", idnum=" + idnum
				+ ", firstcharge=" + firstcharge + ", donateflag=" + donateflag
				+ "]";
	}

}