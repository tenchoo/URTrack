package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.mapper.ext.LaoCustConcatPoExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.po.LaoCustConcatPo;
import com.urt.po.LaoCustomerPo;

@Service("miniCustConcatService")
public class MiniSsCustConcatServiceImpl {
	@Autowired
	LaoCustConcatPoExtMapper dao;
	/**
	 * 功能描述：企业客户联系人分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoCustConcatDto dto, int pageNo, int pageSize) {
		Page<LaoCustConcatPo> page = new Page<LaoCustConcatPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoCustConcatPo) ConversionUtil.dto2po(dto, LaoCustConcatPo.class));
		page.setParams(params);
		List<LaoCustConcatPo> roleList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(roleList, LaoCustConcatDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
