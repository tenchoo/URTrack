package com.urt.interfaces.task;

import com.urt.dto.task.LaoTrafficMMTaskDto;


public interface TrafficTaskService {

	int getTaskCount(long channelCustId, String type,String month, int parseInt,
			int parseInt2);
	Long getTaskId(long channelCustId, String type, String month, int parseInt,
			int parseInt2, String value1, String value2, String operatorId, String goodsId);

	Long saveTaskInfo(Long userId, long channelCustId,String type, String month, int parseInt,
			int parseInt2, String value1, String value2, String operatorId, String goodsId);
	LaoTrafficMMTaskDto getTaskById(Long id);
	void insertBinary(LaoTrafficMMTaskDto taskDto);

	
}
