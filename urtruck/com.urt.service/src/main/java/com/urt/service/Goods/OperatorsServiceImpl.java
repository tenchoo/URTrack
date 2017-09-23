package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.OperatorsDto;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.OperatorsExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Operators;

@Service("operatorsService")
@Transactional(propagation=Propagation.REQUIRED)
public class OperatorsServiceImpl implements OperatorsService{
	
	@Autowired
	private OperatorsExtMapper operatorsExtMapper;
	@Autowired
	private OperatorsMapper operatorsDao;
	@Override
	public List<OperatorsDto> findOperators() {
		List<OperatorsDto> list = new ArrayList<OperatorsDto>();
		List<Operators> findOperators = operatorsExtMapper.findOperators();		
		for (Operators operators : findOperators) {
			OperatorsDto operatorsDto = new OperatorsDto();
			BeanMapper.copy(operators, operatorsDto);
			list.add(operatorsDto);
		}
		return list;
	}
	@Override
	public OperatorsDto selectByPrimaryKey(Integer operatorsId) {
		// TODO Auto-generated method stub
		Operators selectByPrimaryKey = operatorsDao.selectByPrimaryKey(operatorsId);
		OperatorsDto dto=null;
		if(selectByPrimaryKey!=null){
			dto=new OperatorsDto();
			BeanMapper.copy(selectByPrimaryKey, dto);
		}
		return dto;
	}
	


}
