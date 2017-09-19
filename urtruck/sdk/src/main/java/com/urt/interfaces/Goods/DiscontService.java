package com.urt.interfaces.Goods;

import java.util.List;

import com.urt.dto.Goods.DiscontDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface DiscontService {
	/**
	 * 查询优惠
	 * @return
	 */
	public List<DiscontDto> findDiscont();
	
	public DiscontDto queryByDiscontId(Integer originalId);
	
	

}
