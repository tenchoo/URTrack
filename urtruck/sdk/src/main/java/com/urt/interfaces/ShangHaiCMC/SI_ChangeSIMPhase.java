package com.urt.interfaces.ShangHaiCMC;
/**
 * 上海移动 变更 SIM 卡生命周期接口
 */
public interface SI_ChangeSIMPhase {
	/**
	 * 
	 * @param Request_ID
	 * @param Request_Date_Time
	 * @param EID
	 * @param MSISDN
	 * @return
	 */
	public String changeSIMPhase(String eid,String msisdn);
}
