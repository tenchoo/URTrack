package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.OperatorPlanDto;
import com.urt.interfaces.Goods.OperatorPlanService;
import com.urt.mapper.ext.OperatorPlanExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.OperatorPlan;
@Service("operatorPlanService")
@Transactional(propagation=Propagation.REQUIRED)
public class OperatorPlanServiceImpl implements OperatorPlanService{


	@Autowired
	private OperatorPlanExtMapper operatorPlanExtMapper;

	@Override
	public List<OperatorPlanDto> findOperatorPlan(OperatorPlanDto dto) {
		OperatorPlan po=new OperatorPlan();
		BeanMapper.copy(dto, po);
		List<OperatorPlanDto> list = new ArrayList<OperatorPlanDto>();
		List<OperatorPlan> findOperatorPlan = operatorPlanExtMapper.findOperatorPlan(po);
		for (OperatorPlan operatorPlan : findOperatorPlan) {
			OperatorPlanDto operatorPlanDto = new OperatorPlanDto();
			BeanMapper.copy(operatorPlan, operatorPlanDto);
			list.add(operatorPlanDto);
		}
		return list;
	}
	
	
}
