package com.urt.interfaces.ShangHaiCMC;

import com.urt.dto.ShResultDto;

/**
 *变更数据业务状态和 APN接口
 */
public interface SI_ActivateAPN {
	/**
	 * OPER_TYPE为03时变更数据业务状态和 APN
	 * @param MSISDN 手机号码
	 * @param OPER_TYPE 数据业务开关状态    00 开 01关,03 变更
	 * @param APN1_Oper 对 APN1 的操作  00 开 01关
	 * @param APN2_Oper 对 APN2 的操作 00 开 01关
	 * @return
	 */
	public ShResultDto activeStatusAndAPN(String msisdn ,String oper_type ,String apn1_oper ,String apn2_oper);
	/**
	 * OPER_TYPE不为03时变更数据业务状态
	 * @param MSISDN 手机号码
	 * @param OPER_TYPE 数据业务开关状态    00 开 01关,03 变更
	 * @return
	 */
	public ShResultDto activeStatus(String msisdn ,String oper_type);
}
