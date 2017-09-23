package com.urt.service.CustomerCentre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoUserIpManagerDto;
import com.urt.dto.Remain.LaoBAccesslogDto;
import com.urt.interfaces.CustomerCentre.CustomerQueryService;
import com.urt.mapper.ext.LaoBAccesslogExtMapper;
import com.urt.po.LaoBAccesslog;
import com.urt.po.LaoUserIpManager;

@Service("customerQueryService")
public class CustomerQueryServiceImpl implements CustomerQueryService{

	
	@Autowired
	private LaoBAccesslogExtMapper laoBAccesslogExtDao;
	//充值记录查询
	public  Map<String,Object>  queryPageList(LaoBAccesslogDto dto, int pageNo, int pageSize){
		
		Page<LaoBAccesslog> page=new Page<LaoBAccesslog>();
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoUserIpManager) ConversionUtil.dto2po(dto, LaoBAccesslog.class));
		page.setParams(params);
	    List<Map<String, Object>> pageRsult=laoBAccesslogExtDao.queryPageByType(page);
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("data", pageRsult);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
		return resultMap;
		
	}
}
