package com.urt.service.unicom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.FlowConfigMapper;
import com.urt.po.FlowConfig;

@Service("flowConfigService")
@Transactional
public class FlowConfigService {

	@Autowired
	private FlowConfigMapper flowConfigDAO;
	
	public FlowConfig findFlowConfigByParam(String flowSize,String price){
		
		return flowConfigDAO.doQueryFirst(flowSize,price);
	}

	public List<FlowConfig> findFlowConfigList() {
		
		return flowConfigDAO.doQueryList();
	}
	
}
