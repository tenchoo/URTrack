package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class BalanceQRRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String number;				//当前查询的号码
	private Long BALANCE;				//帐户余额
	private Long RETURNED_BALANCE;		//该账户+该号码储备金
	private Long GROUP_BALANCE;			//账目组余额
	private Long IRESULT;				//在处理正确情况下为0，否则是错误
	private String SMSG;				//在处理异常情况下为错误信息，包括错误内容提示、可能的错误原因、解决操作
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Long getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(Long bALANCE) {
		BALANCE = bALANCE;
	}
	public Long getRETURNED_BALANCE() {
		return RETURNED_BALANCE;
	}
	public void setRETURNED_BALANCE(Long rETURNED_BALANCE) {
		RETURNED_BALANCE = rETURNED_BALANCE;
	}
	public Long getGROUP_BALANCE() {
		return GROUP_BALANCE;
	}
	public void setGROUP_BALANCE(Long gROUP_BALANCE) {
		GROUP_BALANCE = gROUP_BALANCE;
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
