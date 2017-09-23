package com.urt.miniService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.constant.SysConstants;
import com.urt.dto.LaoCustomerDto;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.po.LaoCustomerPo;
@Service("miniCutomerService")
public class MiniSsCustomerServiceImpl {
	@Autowired
	LaoCustomerPoExtMapper dao;
	/**
	 * 功能描述：企业客户分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoCustomerDto dto, int pageNo, int pageSize) {
		List<LaoCustomerPo> custList = new ArrayList<LaoCustomerPo>();
		Page<LaoCustomerPo> page = new Page<LaoCustomerPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoCustomerPo po = new LaoCustomerPo();
		po.setCustName(dto.getCustName());
		params.put("param", po);
		page.setParams(params);
		//如果是admin
		if(dto.getCustId().longValue() == SysConstants.superCustId.longValue()){
			custList = dao.queryPage(page);
		}else{
			params.put("pid", dto.getCustId());
			custList = dao.queryPageNormal(page);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(custList, LaoCustomerDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	
	/**
	 * 分润管理使用
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> feeQueryPage(LaoCustomerDto dto, int pageNo, int pageSize) {
		List<LaoCustomerPo> custList = new ArrayList<LaoCustomerPo>();
		Page<LaoCustomerPo> page = new Page<LaoCustomerPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoCustomerPo) ConversionUtil.dto2po(dto, LaoCustomerPo.class));
		page.setParams(params);
		params.put("pid", dto.getCustId());
		custList = dao.feeQueryPage(page);
		for (LaoCustomerPo po : custList) {
			if (po.getCustId().longValue() == dto.getCustId().longValue()){
				custList.remove(po);
				break;
			}
		}
			
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(custList, LaoCustomerDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	
	/**
     * 功能描述：个人客户分页查询
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Map<String, Object> queryPerPage(LaoCustomerDto dto, int pageNo, int pageSize) {
        List<LaoCustomerPo> custList = new ArrayList<LaoCustomerPo>();
        Page<LaoCustomerPo> page = new Page<LaoCustomerPo>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("param", (LaoCustomerPo) ConversionUtil.dto2po(dto, LaoCustomerPo.class));
        page.setParams(params);
        //如果是admin    
        custList = dao.queryPerPage(page);        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", ConversionUtil.poList2dtoList(custList, LaoCustomerDto.class));
        resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
        resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
        return resultMap;
    }
}
