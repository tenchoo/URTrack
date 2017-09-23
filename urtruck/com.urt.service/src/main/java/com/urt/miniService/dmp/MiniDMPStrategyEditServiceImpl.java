package com.urt.miniService.dmp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsRealNameVerifyDto;
import com.urt.dto.dmp.LaoDMPStrategyEditDto;
import com.urt.mapper.ext.LaoCustomerVerifyPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyEditPoExtMapper;
import com.urt.po.LaoCustomerVerifyPo;
import com.urt.po.LaoDMPStrategyEditPo;
@Service("miniDMPStrategyEditServiceImpl")
public class MiniDMPStrategyEditServiceImpl {
	@Autowired
	LaoDMPStrategyEditPoExtMapper dao;
	/**
	 * 功能描述：实名认证分页查询
	 * @author zhaoxy9
	 * @date 2016年6月22日 下午2:30:50
	 * @param @param dto
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return 
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryPage(LaoDMPStrategyEditDto dto, int pageNo, int pageSize) {
		Page<LaoDMPStrategyEditPo> page = new Page<LaoDMPStrategyEditPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoDMPStrategyEditPo) ConversionUtil.dto2po(dto, LaoDMPStrategyEditPo.class));
		page.setParams(params);
		List<LaoDMPStrategyEditPo> strategyEditList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(strategyEditList, LaoDMPStrategyEditDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
