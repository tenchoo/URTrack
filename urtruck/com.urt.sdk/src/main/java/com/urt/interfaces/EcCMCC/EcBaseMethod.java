package com.urt.interfaces.EcCMCC;

public interface EcBaseMethod {
	/**
	 * 当月gprs查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String queryGprsInfo(String msisdn);

	/**
	 * 短信批量查询
	 * 
	 * @param msisdns
	 *            格式 xxx_xxx_xxx
	 * @param date
	 * @return
	 */
	public String batchsmsused(String msisdns, String date);

	/**
	 * 流量信息批量查询
	 * 
	 * @param msisdns
	 *            格式 xxx_xxx_xxx
	 * @param date
	 * @return
	 */
	public String batchgprsused(String msisdns, String date);

	/**
	 * 用户余额信息实时查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String balancerealsingle(String msisdn);

	/**
	 * 码号信息查询
	 * 
	 * @param card_info
	 * @param type
	 *            0 1 2 msisdn imsi iccid
	 * @return
	 */
	public String cardinfo(String card_info, String type);

	/**
	 * 用户状态信息实时查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String userstatusrealsingle(String msisdn);

	/**
	 * 
	 * 在线信息实时查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String gprsrealsingle(String msisdn);

	/**
	 * 套餐内GPRS流量使用情况实时查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String gprsrealtimeinfo(String msisdn);

	/**
	 * 用户短信使用查询
	 * 
	 * @param msisdn
	 * @param query_date
	 * @return
	 */
	public String smsusedbydate(String msisdn, String query_date);

	/**
	 * 用户当月短信查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String smsusedinfosingle(String msisdn);

	/**
	 * 短信状态重置
	 * 
	 * @param msisdn
	 * @return
	 */
	public String smsstatusreset(String msisdn);

	/**
	 * 开关机信息实时查询
	 * 
	 * @param msisdn
	 * @return
	 */
	public String onandoffrealsingle(String msisdn);

	/**
	 * 集团用户数查询
	 * 
	 * @param query_date
	 * @return
	 */
	public String groupuserinfo(String query_date);
	/**
	 * 欠费停机用户批量查询 ,物联卡多APN信息实时查询,物联卡资费套餐查询
	 * 
	 */
	/**
	 * 物联卡资费套餐查询
	 * @param msisdn
	 * @return
	 */
	public String cardPricingPackage(String msisdn);
}
