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
import com.urt.dto.dmp.LaoDMPLogDto;
import com.urt.mapper.ext.LaoDMPLogPoExtMapper;
import com.urt.po.LaoDMPLogPo;
@Service("miniDeviceLogServiceImpl")
public class MiniDeviceLogServiceImpl {
	@Autowired
	LaoDMPLogPoExtMapper dao;
	/**
	 * 功能描述：日志信息分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoDMPLogDto logDto, int pageNo, int pageSize) {
		List<HashMap<String, Object>> logList = new ArrayList<HashMap<String, Object>>();
		Page<LaoDMPLogPo> page = new Page<LaoDMPLogPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoDMPLogPo) ConversionUtil.dto2po(logDto, LaoDMPLogPo.class));
		page.setParams(params);
		logList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", logList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
