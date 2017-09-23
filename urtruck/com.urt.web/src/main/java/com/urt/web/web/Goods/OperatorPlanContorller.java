package com.urt.web.web.Goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.Goods.OperatorPlanDto;
import com.urt.interfaces.Goods.OperatorPlanService;

@Controller
@RequestMapping("/operatorPlan")
public class OperatorPlanContorller {
	
	@Autowired
	private OperatorPlanService operatorPlanService;
	
	
	@RequestMapping("/findOperatorPlan")
	@ResponseBody
	private List<OperatorPlanDto> findOperatorPlan(OperatorPlanDto dto)throws Exception{
		List<OperatorPlanDto> OperatorPlanList = operatorPlanService.findOperatorPlan(dto);
		return OperatorPlanList;
		
	}

}
