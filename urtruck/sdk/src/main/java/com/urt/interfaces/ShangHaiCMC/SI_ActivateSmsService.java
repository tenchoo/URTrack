package com.urt.interfaces.ShangHaiCMC;
/**
 * 短信开关操作接口
 *
 */
public interface SI_ActivateSmsService {
	/**
	 * 
	 * @param MSISDN  手机号
	 * @param OPER_TYPE 0 开 1 关
	 * @param Request_ID
	 * @param Request_Date_Time
	 * @return
	 */
	public String activateSmaService(String msisdn ,String oper_type );
}
