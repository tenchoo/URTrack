package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Operators;
import com.urt.po.ServiceStatus;

@Service("serviceStatusService")
@Transactional(propagation=Propagation.REQUIRED)
public class ServiceStatusServiceImpl implements ServiceStatusService {
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtDAO;

	@Override
	public List<ServiceStatusDto> selectByOperatorId(String operatorId) {
		List<ServiceStatusDto> list = null;
		List<ServiceStatus> findOperators = serviceStatusExtDAO.selectByOperatorId(operatorId);
		if(findOperators !=null && findOperators.size()> 0){
			list = new ArrayList<ServiceStatusDto>();
			for (ServiceStatus serviceStatus : findOperators) {
				ServiceStatusDto sDto = new ServiceStatusDto();
				BeanMapper.copy(serviceStatus, sDto);
				list.add(sDto);
			}
		}
		return list;
	}

	@Override
	public ServiceStatusDto queryState(Integer serviceId, String stateCode) {
		ServiceStatusDto  serviceStatusDto = null;
		ServiceStatus queryState = serviceStatusExtDAO.queryState(serviceId, stateCode);
		if(queryState != null){
			serviceStatusDto = new ServiceStatusDto();
			BeanMapper.copy(queryState, serviceStatusDto);
		}
		return serviceStatusDto;
	}
}
