package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoAlmRuleDto;
import com.urt.dto.LaoAlmRuleLogDto;
import com.urt.mapper.ext.LaoAlmRuleLogPoExtMapper;
import com.urt.mapper.ext.LaoAlmRulePoExtMapper;
import com.urt.po.LaoAlmRuleLogPo;
import com.urt.po.LaoAlmRulePo;
import com.urt.po.LaoCustomerPo;

@Service(value="miniAlarmService")
public class MiniAlarmServiceImpl {
	@Autowired
	LaoAlmRulePoExtMapper dao;
	@Autowired
	LaoAlmRuleLogPoExtMapper almRuleLogPoExtMapper;
	/**
	 * 功能描述：企业客户分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoAlmRuleDto dto, int pageNo, int pageSize) {
		Page<LaoAlmRulePo> page = new Page<LaoAlmRulePo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoAlmRulePo) ConversionUtil.dto2po(dto, LaoAlmRulePo.class));
		page.setParams(params);
		List<Map<String, Object>> alarms = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", alarms);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	public Map<String, Object> queryLogPage(LaoAlmRuleLogDto almRuleLogDto,
			int pageNo, int pageSize) {
		Page<LaoAlmRuleLogPo> page = new Page<LaoAlmRuleLogPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param",ConversionUtil.dto2po(almRuleLogDto, LaoAlmRuleLogPo.class));
		page.setParams(params);
		List<Map<String, Object>> alarms = almRuleLogPoExtMapper.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", alarms);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
