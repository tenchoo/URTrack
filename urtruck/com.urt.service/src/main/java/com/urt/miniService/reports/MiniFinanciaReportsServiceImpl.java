package com.urt.miniService.reports;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.reports.laoUserOrderMmDto;
import com.urt.mapper.ext.laoUserOrderMmPoExtMapper;
import com.urt.po.laoUserOrderMmPo;
@Service("minFinanciaReportsImpl")
public class MiniFinanciaReportsServiceImpl {
	@Autowired
	laoUserOrderMmPoExtMapper dao;
	/**
	 * 功能描述：财务报表分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(laoUserOrderMmDto dto, int pageNo, int pageSize) {
		Page<laoUserOrderMmPo> page = new Page<laoUserOrderMmPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		laoUserOrderMmPo po = (laoUserOrderMmPo) ConversionUtil.dto2po(dto, laoUserOrderMmPo.class);
		params.put("param", po);
		page.setParams(params);
		dao.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageDate", page);
		return resultMap;
	}
}
