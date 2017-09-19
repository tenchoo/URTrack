package com.urt.interfaces.ShangHaiCMC;
/**
 * SIM卡销户
 */
public interface SI_TerminateSIM {
	/**
	 * 
	 * @param MSISDN
	 * @param OPER_TYPE
	 * @return
	 */
	public String terminateSIM(String msisdn,String oper_type);
}
