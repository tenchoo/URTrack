package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.mapper.ext.LaoDMPGroupPoExtMapper;
import com.urt.po.LaoDMPGroupPo;
@Service("minDMPDeviceManageImpl")
public class MiniDeviceManageServiceImpl {
	@Autowired
	LaoDMPGroupPoExtMapper dao;
	/**
	 * 功能描述：设备组分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoDMPGroupDto dto, int pageNo, int pageSize) {
		List<HashMap<String, Object>> deviceManageList = null;
		Page<LaoDMPGroupPo> page = new Page<LaoDMPGroupPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoDMPGroupPo po = (LaoDMPGroupPo) ConversionUtil.dto2po(dto, LaoDMPGroupPo.class);
		params.put("param", po);
		page.setParams(params);
		deviceManageList = dao.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",deviceManageList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
