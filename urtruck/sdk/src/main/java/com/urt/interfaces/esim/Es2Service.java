package com.urt.interfaces.esim;

public interface Es2Service {
	/**
	 * 确认绑定 
	 * @param iccid
	 * @return
	 */
	public String confirmOrder(String iccid);
	/**
	 * 解绑接口
	 * @param iccid
	 * @param eid
	 * @return
	 */
	public String cancelOrder(String iccid, String eid,String matchingId );
	/**
	 * 预绑定
	 * @param iccid
	 * @param eid
	 * @return
	 */
	public String downloadOrder(String iccid, String eid);
	/**
	 * 发布profile
	 * @param iccid
	 * @return
	 */
	
	public String releaseProfile(String iccid);
}
