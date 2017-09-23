package com.urt.interfaces.ShangHaiCMC;
/**
 * 语音功能开通及关闭接口
 *
 */
public interface SI_ActivateVoiceService {
	/**
	 * 
	 * @param Request_ID
	 * @param MSISDN  手机号
	 * @param OprCode 01：语音服务开通 02：语音服务关闭 03：语音服务变更
	 * @param OprTime
	 * @param IntRoamStatus  01：开通，02：关闭
	 * @param CLIPStatus   01：开通；02：关闭
	 * @param OCSIPROV     01：开通；02：关闭；03：变更
	 * @param OCSITPLID    当 OCSIPROV 为01、03 时，为必填
	 * @param TCSIPROV     01：开通；02：关闭；03：变更
	 * @param TCSITPLID    当 TCSIPROV 为01、03 时，为必填
	 * @param Request_Date_Time
	 * @return
	 */
	public String activeVoiceService(String msisdn,String oprcode,
			String oprtime,String introamstatus,String clipstatus,String ocsiprov,
			String ocsitplid,String tcsiprov,String tcsitplid);
}
