package com.urt.miniService.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.reports.LaoUserExpireMMDto;
import com.urt.mapper.ext.LaoUserExpireMMPoExtMapper;
import com.urt.po.LaoUserExpireMMPo;

@Service("minExpireServiceImpl")
public class MiniExpireServiceImpl {
	@Autowired
	LaoUserExpireMMPoExtMapper dao;
	
	public Map<String, Object> queryPage(LaoUserExpireMMDto dto, int pageNo, int pageSize) {
		List<HashMap<String, Object>> expireList = null;
		Page<LaoUserExpireMMPo> page = new Page<LaoUserExpireMMPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoUserExpireMMPo po = (LaoUserExpireMMPo) ConversionUtil.dto2po(dto, LaoUserExpireMMPo.class);
		params.put("param", po);
		page.setParams(params);
		expireList = dao.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",expireList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
