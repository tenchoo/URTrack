package com.urt.interfaces.ShangHaiCMC;
/**
 * 恢复SIM卡
 *
 */
public interface SI_ResumeSIM {
	/**
	 * 
	 * @param MSISDN
	 * @param OPER_TYPE
	 * @return
	 */
	public String resumeSIM(String msisdn,String oper_type);
}
