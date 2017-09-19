package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.Trade.TradeDto;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.po.Trade;
@Service("miniTradeRecordServiceImpl")
public class MinTradeRecordServiceImpl {
	@Autowired
	TradeExtMapper dao;
	/**
	 * 功能描述：订购分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(TradeDto dto, int pageNo, int pageSize,String monthId) {
		List<HashMap<String, Object>> recordList = null;
//		Page<Trade> page = new Page<Trade>();
		Map<String,Object> page=new HashMap<String, Object>();
		page.put("pageNo",pageNo);
		page.put("pageSize",pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (Trade) ConversionUtil.dto2po(dto, Trade.class));
		params.put("monthId",monthId);
		page.put("params", params);
		long totalRecord=dao.getTotalRecord(page);
		recordList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", recordList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", totalRecord);//总记录数 
		return resultMap;
	}
}
