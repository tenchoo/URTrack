package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class CurrAcuRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String number;				//当前查询的号码
	private String OFFER_NAME;			//套餐
	private String ACCU_NAME;			//优惠名称
	private String START_TIME;			//开始时间
	private String END_TIME;			//结束时间
	private String UNIT_NAME;			//单位 流量套餐为KB,语音套餐为分钟，短信套餐为条
	private String CUMULATION_TOTAL;	//累积量总量
	private String CUMULATION_ALREADY;	//已使用累积量
	private String CUMULATION_LEFT;		//剩余累积量
	private String OFFER_TYPE;			//0为互联网流量套餐 	1为定向流量套餐
	private Long IRESULT;				//在处理正确情况下为0，否则是错误代码
	private String SMSG;				//在处理异常情况下为错误信息，包括错误内容提示、可能的错误原因、解决操作
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOFFER_NAME() {
		return OFFER_NAME;
	}
	public void setOFFER_NAME(String oFFER_NAME) {
		OFFER_NAME = oFFER_NAME;
	}
	public String getACCU_NAME() {
		return ACCU_NAME;
	}
	public void setACCU_NAME(String aCCU_NAME) {
		ACCU_NAME = aCCU_NAME;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getUNIT_NAME() {
		return UNIT_NAME;
	}
	public void setUNIT_NAME(String uNIT_NAME) {
		UNIT_NAME = uNIT_NAME;
	}
	public String getCUMULATION_TOTAL() {
		return CUMULATION_TOTAL;
	}
	public void setCUMULATION_TOTAL(String cUMULATION_TOTAL) {
		CUMULATION_TOTAL = cUMULATION_TOTAL;
	}
	public String getCUMULATION_ALREADY() {
		return CUMULATION_ALREADY;
	}
	public void setCUMULATION_ALREADY(String cUMULATION_ALREADY) {
		CUMULATION_ALREADY = cUMULATION_ALREADY;
	}
	public String getCUMULATION_LEFT() {
		return CUMULATION_LEFT;
	}
	public void setCUMULATION_LEFT(String cUMULATION_LEFT) {
		CUMULATION_LEFT = cUMULATION_LEFT;
	}
	public String getOFFER_TYPE() {
		return OFFER_TYPE;
	}
	public void setOFFER_TYPE(String oFFER_TYPE) {
		OFFER_TYPE = oFFER_TYPE;
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
