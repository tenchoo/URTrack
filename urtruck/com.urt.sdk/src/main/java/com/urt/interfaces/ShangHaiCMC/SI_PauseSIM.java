package com.urt.interfaces.ShangHaiCMC;
/**
 * 暂停SIM卡
 *
 */
public interface SI_PauseSIM {
	/**
	 * 
	 * @param MSISDN
	 * @param OPER_TYPE
	 * @return
	 */
	public String pauseSIM(String msisdn,String oper_type);
}
