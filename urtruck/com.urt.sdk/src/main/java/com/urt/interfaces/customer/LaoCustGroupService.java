package com.urt.interfaces.customer;

import java.util.List;

import com.urt.dto.LaoCustGroupDto;

public interface LaoCustGroupService {
	int save(LaoCustGroupDto dto);
	
	int update(LaoCustGroupDto dto);
	
	LaoCustGroupDto selectDtoById(Long custId);

    List<LaoCustGroupDto> queryChannelCust(String sellType);
}
