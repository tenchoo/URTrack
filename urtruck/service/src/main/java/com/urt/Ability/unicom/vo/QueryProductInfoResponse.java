package com.urt.Ability.unicom.vo;

import java.io.Serializable;

public class QueryProductInfoResponse extends WsResponse implements
		Serializable {

	private static final long serialVersionUID = -9111221611342510659L;
	private String version;
	private String resultcode;
	private String resultnotes;
	private String modeltypename;
	private String modeltype;
	private String catalogname;
	private String productname;
	private String brandtypeid;
	private String prductdate;
	private String productlineid;
	private String packinglotno;
	private String productsn;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getResultnotes() {
		return resultnotes;
	}

	public void setResultnotes(String resultnotes) {
		this.resultnotes = resultnotes;
	}

	public String getModeltypename() {
		return modeltypename;
	}

	public void setModeltypename(String modeltypename) {
		this.modeltypename = modeltypename;
	}

	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}

	public String getCatalogname() {
		return catalogname;
	}

	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getBrandtypeid() {
		return brandtypeid;
	}

	public void setBrandtypeid(String brandtypeid) {
		this.brandtypeid = brandtypeid;
	}

	public String getPrductdate() {
		return prductdate;
	}

	public void setPrductdate(String prductdate) {
		this.prductdate = prductdate;
	}

	public String getProductlineid() {
		return productlineid;
	}

	public void setProductlineid(String productlineid) {
		this.productlineid = productlineid;
	}

	public String getPackinglotno() {
		return packinglotno;
	}

	public void setPackinglotno(String packinglotno) {
		this.packinglotno = packinglotno;
	}

	public String getProductsn() {
		return productsn;
	}

	public void setProductsn(String productsn) {
		this.productsn = productsn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
