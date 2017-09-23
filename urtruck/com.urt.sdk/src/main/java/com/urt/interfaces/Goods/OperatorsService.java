package com.urt.interfaces.Goods;

import java.util.List;

import com.urt.dto.Goods.OperatorsDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface OperatorsService {
	
	public List<OperatorsDto> findOperators();
	
	public OperatorsDto selectByPrimaryKey(Integer operatorsId);

}
