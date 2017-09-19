package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoCustomerNoticeDto;
import com.urt.mapper.ext.LaoCustomerNoticePoExtMapper;
import com.urt.po.LaoCustomerNoticePo;
@Service("minCustNoticeImpl")
public class MiniCustNoticeServiceImpl {
	@Autowired
	LaoCustomerNoticePoExtMapper dao;
	/**
	 * 功能描述：重要通知分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoCustomerNoticeDto dto, int pageNo, int pageSize) {
		Page<LaoCustomerNoticePo> page = new Page<LaoCustomerNoticePo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoCustomerNoticePo po = (LaoCustomerNoticePo) ConversionUtil.dto2po(dto, LaoCustomerNoticePo.class);
		params.put("param", po);
		page.setParams(params);
		dao.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*resultMap.put("data",noticeList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
*/		resultMap.put("pageDate", page);
		return resultMap;
	}
}
