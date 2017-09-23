package com.urt.interfaces.order;

import com.urt.dto.LaoOperatordealLogDto;

public interface OrderService {
	void sendMsg(String requestId);
	
	LaoOperatordealLogDto getDealLogByRequestId(String requestId);
	
	int updateFailNumByRequestId(String requestId);
}
