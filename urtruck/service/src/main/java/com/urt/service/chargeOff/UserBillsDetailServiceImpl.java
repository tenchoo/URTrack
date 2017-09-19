package com.urt.service.chargeOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.chargeOff.LaoUserbillsDetailDto;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.chargeOff.UserBillsDetailService;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.ext.LaoUserbillsDetailExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoUserbillsDetail;
import com.urt.po.LaoUserbillsDetailPo;

/**
* 功能描述：用户账单实现类
* 类名：UserBillsDetailServiceImpl 
* @author sunhao
* @date 2017年3月7日
 */
@Service("userBillsDetailService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserBillsDetailServiceImpl implements UserBillsDetailService {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoUserbillsDetailExtMapper laoUserbillsDetailExtDAO;
	
	@Autowired
	private LaoUserMapper laoUserDAO;
	
	@Autowired
	private OperatorsService operatorService;
	
	@Autowired
	private LaoCustomerPoMapper laoCustomerPoDAO;
	
	@Autowired
	private ServiceStatusMapper ServiceStatusDAO;
	
	@Autowired
	private GoodsMapper goodsDAO;
	
	@Override
	public Map<String, Object> queryUserBillsByPage(LaoUserbillsDetailDto dto, int pageNo, int pageSize) {
		Page<LaoUserbillsDetail> page = new Page<LaoUserbillsDetail>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoUserbillsDetail userBillsDetail = new LaoUserbillsDetail();
		if (dto != null) {
			BeanMapper.copy(dto, userBillsDetail);
		}
		params.put("userbill", userBillsDetail);
		page.setParams(params);
		List<LaoUserbillsDetailPo> list = laoUserbillsDetailExtDAO.queryUserBillsDetailsByPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(list != null && list.size() > 0){
			resultMap.put("data", ConversionUtil.poList2dtoList(list,LaoUserbillsDetailDto.class));
		}else{
			resultMap.put("data", new ArrayList<LaoUserbillsDetailDto>());
		}
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
		return resultMap;
	}

	@Override
	public List<LaoUserbillsDetailDto> queryUserBills(LaoUserbillsDetailDto dto) {
		LaoUserbillsDetail userBillsDetail = new LaoUserbillsDetail();
		if (dto != null) {
			BeanMapper.copy(dto, userBillsDetail);
		}
		List<LaoUserbillsDetailPo> list = laoUserbillsDetailExtDAO.queryUserBillsDetails(userBillsDetail);
		if(list != null && list.size() > 0){
			return ConversionUtil.poList2dtoList(list,LaoUserbillsDetailDto.class);
		}
		return null;
	}

	@Override
	public LaoUserbillsDetailDto getUserBillDetailByChargeId(String chargeId) {
		LaoUserbillsDetailDto dto = null;
		LaoUserbillsDetail userBillDetailByChargeId = laoUserbillsDetailExtDAO.getUserBillDetailByChargeId(chargeId);
		if(userBillDetailByChargeId != null){
			dto = new LaoUserbillsDetailDto();
			BeanMapper.copy(userBillDetailByChargeId, dto);
		}
		return dto;
	}

	@Override
	public Long countTotal(Long custId, Integer cycleId, Integer operatosId) {
		// TODO Auto-generated method stub
		return laoUserbillsDetailExtDAO.countTotal(custId, cycleId, operatosId);
	}

}
