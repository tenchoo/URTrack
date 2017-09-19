package com.urt.interfaces.ShangHaiCMC;
/**
 * 流量池流量查询
 */
public interface SI_QueryDataPoolUsage {
	/**
	 * 
	 * @param EID
	 * @param PoolId
	 * @return
	 */
	public String queryDataPoolUsage(String eid,String poolId);
}
