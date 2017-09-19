package com.urt.interfaces.traffic;

import java.util.Map;

import com.urt.dto.traffic.LaoTrafficDetailDto;

public interface BatchInsertTrafficQueryService {

	int insertSelective(LaoTrafficDetailDto record);
	
	int update(LaoTrafficDetailDto record);
	
	LaoTrafficDetailDto selectByIccidAndCycle(Map<String,Object> map);
}
