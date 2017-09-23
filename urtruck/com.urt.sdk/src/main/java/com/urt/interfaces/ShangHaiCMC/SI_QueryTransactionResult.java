package com.urt.interfaces.ShangHaiCMC;
/**
 *查询套餐转换交易结果接口
 */
public interface SI_QueryTransactionResult {
	/**
	 * 
	 * @param MSISDN
	 * @param Old_Request_ID
	 * @return
	 */
	public String queryTransactionResult(String msisdn,String old_request_id);
}
