package com.urt.interfaces.ShangHaiCMC;
/**
 * 号码开关机状态查询
 *
 */
public interface SI_QuerySubscriberStatus {
	/**
	 * 
	 * @param MSISDN
	 * @param Request_ID
	 * @param Request_Date_Time
	 * @return
	 */
	public String querySubscriberStatus(String msisdn);
}
