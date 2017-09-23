package com.urt.interfaces.traffic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.LaoTrafficMmDto;

public interface TimerTrafficQueryService {

	// 定时 批量查询流量
	LaoTrafficDetailDto doTrafficQuery (LaoUserDto user);
	
	public void sendUserMsg(List<Map<String, Object>> listMap);
	// 记录每月记录
    int insertSelective(LaoTrafficMmDto record);
    // 根据userId查询月记录
    LaoTrafficMmDto selectByUseId(Long userId, String dataCycleMm);
    // 更新每月记录(每天更新累计使用流量)
    int updateByPrimaryKeySelective(LaoTrafficMmDto record);
    
    BigDecimal getNotSendFlow(Long userId);
	
}
