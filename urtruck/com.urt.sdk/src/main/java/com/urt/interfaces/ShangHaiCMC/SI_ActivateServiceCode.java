package com.urt.interfaces.ShangHaiCMC;

import java.util.Map;

/**
 *号码 APN 分应用控制管理
 */
public interface SI_ActivateServiceCode {
	/**
	 * 
	 * @param Request_ID
	 * @param Request_Date_Time
	 * @param MultiRecords { MSISDN, Service_Code, Oper_Type}
	 * 						
	 * @return
	 */
	public String activateServiceCode(Map<String,String> multirecords);
}
