package com.urt.interfaces.feeSplit;

import java.util.Map;

import com.urt.dto.LaoCustomerDto;



public interface FeeSplitService {

	public Map<String, Object> queryPage(LaoCustomerDto dto,Integer pageNo,Integer pageSize);

}
