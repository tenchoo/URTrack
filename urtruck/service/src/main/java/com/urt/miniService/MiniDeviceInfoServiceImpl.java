package com.urt.miniService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.dmp.LaoDMPCardDto;
import com.urt.mapper.ext.LaoDMPCardPoExtMapper;
import com.urt.po.LaoDMPCardPo;
import com.urt.service.dmp.mqttutil.MQTTClint;
import com.urt.service.dmp.mqttutil.MqttConfig;
import com.urt.service.dmp.mqttutil.PublishManager;
@Service("miniDeviceInfoServiceImpl")
public class MiniDeviceInfoServiceImpl {
	@Autowired
	LaoDMPCardPoExtMapper dao;
	/**
	 * 功能描述：设备信息分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoDMPCardDto dto, int pageNo, int pageSize) {
		List<HashMap<String, Object>> deviceInfoList = null;
		Page<LaoDMPCardPo> page = new Page<LaoDMPCardPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoDMPCardPo) ConversionUtil.dto2po(dto, LaoDMPCardPo.class));
		page.setParams(params);
		deviceInfoList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceInfoList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
