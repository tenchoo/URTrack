package com.urt.interfaces.ShangHaiCMC;
/**
 *号码多 APN 状态查询
 */
public interface SI_QuerySubscriberInfo {
	/**
	 * 
	 * @param MSISDN
	 * @return
	 */
	public String querySubscriberInfo(String msisdn);
}
