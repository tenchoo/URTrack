package com.urt.interfaces.ShangHaiCMC;
/**
 * 查询当前有效套餐订单及流量信息
 *
 */
public interface SI_QueryPackageOrderUsage {
	/**
	 * 
	 * @param MSISDN 手机号
	 * @return
	 */
	public String queryPacksgeOrderUsage(String msisdn);
}
