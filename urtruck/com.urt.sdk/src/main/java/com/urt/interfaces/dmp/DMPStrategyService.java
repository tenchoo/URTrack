package com.urt.interfaces.dmp;

import com.urt.dto.dmp.LaoDMPStrategyDto;



/**
 * 终端管理策略处理顶层接口
 * @author Administrator
 *
 */
public interface DMPStrategyService {
	/**
	 * 判断是否违规
	 * @param strategyDto
	 * @param imei
	 * @param custId
	 * @param schemeId
	 * @param groupId
	 */
	void StrategyExecute(LaoDMPStrategyDto strategyDto, String imei,String custId,Long schemeId,Long groupId);
	
}
