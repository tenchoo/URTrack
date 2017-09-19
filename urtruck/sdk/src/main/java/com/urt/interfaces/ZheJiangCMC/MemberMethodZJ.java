package com.urt.interfaces.ZheJiangCMC;




public interface MemberMethodZJ {
	/**
	 * GPRS开启
	 * @param msisdn
	 * @return
	 */
	public  String  resetGPRS(String msisdn);
	/**
	 * GPRS停复机
	 * @param msisdn
	 * @param oper_type T0022：营业停机K0022：营业复机
	 * @return
	 */
	public  String stopGPRS(String msisdn, String oper_type);
	/**
	 * 物联网沉默期激活
	 * @param msisdn
	 * @return
	 */
	public  String cardActive(String msisdn);
	/**
	 * 用户三户信息查询
	 * @param msisdn
	 * @return
	 */
	public  String msisdnInfo(String msisdn);
	/**
	 * 物联网测试期延长和终止
	 * @param msisdn
	 * @param oper_type 延长月数 0：立即停机 1：延长1月 2：延长2月
	 * @return
	 */
	public  String msisdnInfo(String msisdn,String oper_type);
	/**
	 * 物联网沉默期转测试期
	 * @param msisdn
	 * @return
	 */
	public  String msisdnPeriodOfSilence(String msisdn);
	/**
	 * 用户余额查询
	 * @param msisdn
	 * @return
	 */
	public  String msisdnMoneyQuery(String msisdn);
	/**
	 * 物联网卡套餐内GPRS流量使用情况实时查询
	 * @param msisdn
	 * @return
	 */
	public  String msisdnQueryFlow(String msisdn);
	/**
	 * 短信GPRS资源使用量查询
	 * @param msisdn
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	public  String gprsFlowQueryInfo(String msisdn,String begin_date,String end_date);
 
}
