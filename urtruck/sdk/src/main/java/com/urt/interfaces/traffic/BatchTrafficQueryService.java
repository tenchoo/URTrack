package com.urt.interfaces.traffic;

import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.traffic.LaoSimDateDetailDto;

public interface BatchTrafficQueryService {

	// 定时 批量查询流量
	LaoSimDateDetailDto doTrafficQuery (String iccid,String data,String monthCycle);
	// 记录每月记录
    //int insertSelective(LaoTrafficMmDto record);
    // 根据userId查询月记录
    //LaoTrafficMmDto selectByUseId(Long userId, String dataCycleMm);
    // 更新每月记录(每天更新累计使用流量)
    //int updateByPrimaryKeySelective(LaoTrafficMmDto record);
    
    int insertSelectiveDevice(LaoDeviceRelDto record);	
    
    LaoDeviceRelDto selectByUserIdAndidType(Long userId,String idType);
    
    int updateByPrimaryKeySelective(LaoDeviceRelDto record);
}
