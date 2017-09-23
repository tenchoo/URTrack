package com.urt.interfaces.ShangHaiCMC;
/**
 * 上海移动套餐订单管理接口
 */
public interface SI_OrderPackage {
	/**
	 * @param Effective_Code 1 :Immediately(立即生效) 
	 * 						 2 : Next month(下月生效)
	 * 
	 * @param Transaction_Type 11 : Add(新增)
	 *						   12 : Cancel(取消)
	 *					       13 : Change(变更)
	 * @param APN_ID APN的ID
	 * @return
	 */
	public String orderPackage(String msisdn ,String original_package_code ,String target_package_code ,String effective_code ,
			String transaction_type,String apn_id);
}
