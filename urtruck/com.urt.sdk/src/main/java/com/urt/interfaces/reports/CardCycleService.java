package com.urt.interfaces.reports;

import java.util.List;
import java.util.Map;

import com.urt.dto.Goods.LaoUserDto;


public interface CardCycleService {

	List<Map<String, Object>> queryCardsCycle(LaoUserDto userDto);

	List<Map<String, Object>> selectCountByState(LaoUserDto userDto);
	
}
