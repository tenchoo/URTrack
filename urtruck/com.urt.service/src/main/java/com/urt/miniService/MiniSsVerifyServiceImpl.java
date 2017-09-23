package com.urt.miniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsRealNameVerifyDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.mapper.RealNameVerifyMapper;
import com.urt.mapper.ext.LaoCustomerVerifyPoExtMapper;
import com.urt.po.LaoCustomerVerifyPo;
import com.urt.po.LaoSsRolePo;
@Service("miniSsVerifyServiceImpl")
public class MiniSsVerifyServiceImpl {
	@Autowired
	LaoCustomerVerifyPoExtMapper dao;
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
	public Map<String, Object> queryPage(LaoSsRealNameVerifyDto dto, int pageNo, int pageSize) {
		Page<LaoCustomerVerifyPo> page = new Page<LaoCustomerVerifyPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoCustomerVerifyPo) ConversionUtil.dto2po(dto, LaoCustomerVerifyPo.class));
		page.setParams(params);
		List<LaoCustomerVerifyPo> roleList = dao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(roleList, LaoSsRealNameVerifyDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
