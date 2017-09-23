package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urt.po.LaoBillResult;


public interface LaoBillResultExtMapper {
	int selectNeedReturn(Map<String,Object> paramMap);
	int selectReturned(Map<String,Object> paramMap);
	List<LaoBillResult> selectByCustId(Map<String, Object> paraMap);
	List<LaoBillResult> selectByCustIdAndCashBackTag(HashMap<String, Object> paraMap);
	int countBillResByCustId(HashMap<String, Object> paraMap);
	int countByCustId(Map<String, Object> paraMap); //传参: 开始， 结束时间   分页查询
	
	
	/**
	 * 查询当前月已返现总钱数
	 * 说明关键字段列表  cashback_tag    1 标识返现  2标识已返现
	 * @param custId
	 * @return
	 */
	Long currentMonthReturnMoneyByCustId(Long custId);
}