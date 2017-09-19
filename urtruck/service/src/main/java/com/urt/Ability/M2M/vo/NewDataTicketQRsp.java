package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class NewDataTicketQRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String number;				//当前查询的号码
	private String TOTAL_BYTES_CNT;		//格式为*.**MB
	private String DURATION_CNT_CH;		//格式为HH24小时MI分SS秒
	private String CHARGE_CNT_CH;		//格式为0.00元
	private Long TICKET_NUMBER;		
	private String TICKET_TYPE;			//形如：南京等
	private String SERVICE_TYPE;		//3G或者1X 
	private String START_TIME;			//格式为YYYY-MM-DD HH24:MI
	private String DURATION_CH;			//格式为HH24小时MI分SS秒
	private String TICKET_CHARGE_CH;	//格式为0.00元
	private String BYTES_CNT;			//格式为*GB*MB*KB
	private String CCG_PRODUCT_NAME;	//CCG_PRODUCT_DESC
	private Long IRESULT;				//在处理正确情况下为0，否则是错误代码
	private String SMSG;				//在处理异常情况下为错误信息，包括错误内容提示、可能的错误原因、解决操作
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTOTAL_BYTES_CNT() {
		return TOTAL_BYTES_CNT;
	}
	public void setTOTAL_BYTES_CNT(String tOTAL_BYTES_CNT) {
		TOTAL_BYTES_CNT = tOTAL_BYTES_CNT;
	}
	public String getDURATION_CNT_CH() {
		return DURATION_CNT_CH;
	}
	public void setDURATION_CNT_CH(String dURATION_CNT_CH) {
		DURATION_CNT_CH = dURATION_CNT_CH;
	}
	public String getCHARGE_CNT_CH() {
		return CHARGE_CNT_CH;
	}
	public void setCHARGE_CNT_CH(String cHARGE_CNT_CH) {
		CHARGE_CNT_CH = cHARGE_CNT_CH;
	}
	public Long getTICKET_NUMBER() {
		return TICKET_NUMBER;
	}
	public void setTICKET_NUMBER(Long tICKET_NUMBER) {
		TICKET_NUMBER = tICKET_NUMBER;
	}
	public String getTICKET_TYPE() {
		return TICKET_TYPE;
	}
	public void setTICKET_TYPE(String tICKET_TYPE) {
		TICKET_TYPE = tICKET_TYPE;
	}
	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}
	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getDURATION_CH() {
		return DURATION_CH;
	}
	public void setDURATION_CH(String dURATION_CH) {
		DURATION_CH = dURATION_CH;
	}
	public String getTICKET_CHARGE_CH() {
		return TICKET_CHARGE_CH;
	}
	public void setTICKET_CHARGE_CH(String tICKET_CHARGE_CH) {
		TICKET_CHARGE_CH = tICKET_CHARGE_CH;
	}
	public String getBYTES_CNT() {
		return BYTES_CNT;
	}
	public void setBYTES_CNT(String bYTES_CNT) {
		BYTES_CNT = bYTES_CNT;
	}
	public String getCCG_PRODUCT_NAME() {
		return CCG_PRODUCT_NAME;
	}
	public void setCCG_PRODUCT_NAME(String cCG_PRODUCT_NAME) {
		CCG_PRODUCT_NAME = cCG_PRODUCT_NAME;
	}
	public Long getIRESULT() {
		return IRESULT;
	}
	public void setIRESULT(Long iRESULT) {
		IRESULT = iRESULT;
	}
	public String getSMSG() {
		return SMSG;
	}
	public void setSMSG(String sMSG) {
		SMSG = sMSG;
	}

}
