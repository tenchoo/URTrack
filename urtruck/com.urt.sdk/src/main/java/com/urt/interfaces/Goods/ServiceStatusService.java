package com.urt.interfaces.Goods;

import java.util.List;

import com.urt.dto.Goods.ServiceStatusDto;


public interface ServiceStatusService {
	List<ServiceStatusDto> selectByOperatorId(String operatorId);
	public ServiceStatusDto queryState(Integer serviceId, String stateCode);
}
