package com.urt.interfaces.ShangHaiCMC;

import java.util.List;
import java.util.Map;

/**
 * 流量池成员查询接口
 * 查询流量池全量列表或某个码号是否在流量池内
 *
 */
public interface SI_QueryDataPoolMembers {
	/**
	 *
	 * @param EID 当前企业 ID
	 * @param Pool_ID 该企业当前可用的流量池 ID
	 * @param msisdn 待查询的车机号码 (MSISDN 手机号码)
	 * @return
	 */
	public String queryDataPoolMembers(String eid ,List<String> pool_id ,String msisdn);
}
