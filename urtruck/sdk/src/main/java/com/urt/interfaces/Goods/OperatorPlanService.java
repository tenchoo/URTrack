package com.urt.interfaces.Goods;

import java.util.List;

import com.urt.dto.Goods.OperatorPlanDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface OperatorPlanService {
	
	public List<OperatorPlanDto> findOperatorPlan(OperatorPlanDto dto);

}
