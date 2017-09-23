package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class RealNameVerify implements Serializable {
    private Short id;

    private String userid;

    private Short idtype;

    private String realname;

    private String idnum;

    private String handpicurl;

    private String frontpicurl;

    private String backpicurl;

    private String tel;

    private Short verifystatus;

    private Long failtimes;

    private Date createtime;

    private Date updatetime;

    private String iccid;

    private String photourl;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Short getIdtype() {
        return idtype;
    }

    public void setIdtype(Short idtype) {
        this.idtype = idtype;
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

    public String getHandpicurl() {
        return handpicurl;
    }

    public void setHandpicurl(String handpicurl) {
        this.handpicurl = handpicurl == null ? null : handpicurl.trim();
    }

    public String getFrontpicurl() {
        return frontpicurl;
    }

    public void setFrontpicurl(String frontpicurl) {
        this.frontpicurl = frontpicurl == null ? null : frontpicurl.trim();
    }

    public String getBackpicurl() {
        return backpicurl;
    }

    public void setBackpicurl(String backpicurl) {
        this.backpicurl = backpicurl == null ? null : backpicurl.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Short getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(Short verifystatus) {
        this.verifystatus = verifystatus;
    }

    public Long getFailtimes() {
        return failtimes;
    }

    public void setFailtimes(Long failtimes) {
        this.failtimes = failtimes;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl == null ? null : photourl.trim();
    }

	@Override
	public String toString() {
		return "RealNameVerify [id=" + id + ", userid=" + userid + ", idtype="
				+ idtype + ", realname=" + realname + ", idnum=" + idnum
				+ ", handpicurl=" + handpicurl + ", frontpicurl=" + frontpicurl
				+ ", backpicurl=" + backpicurl + ", tel=" + tel
				+ ", verifystatus=" + verifystatus + ", failtimes=" + failtimes
				+ ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", iccid=" + iccid + ", photourl=" + photourl + "]";
	}
    
}