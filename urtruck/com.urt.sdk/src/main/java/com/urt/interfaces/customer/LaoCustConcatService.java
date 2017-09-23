package com.urt.interfaces.customer;

import java.util.Map;

import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustomerDto;

public interface LaoCustConcatService {
	public Map<String, Object> queryPage(LaoCustConcatDto dto,Integer pageNo,Integer pageSize);
	
	int save(LaoCustConcatDto dto);
	
	int update(LaoCustConcatDto dto);
	
	LaoCustConcatDto selectDtoById(Long custId);
	 
	int del(Long contactId);
}
