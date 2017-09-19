package com.urt.interfaces.chargeOff;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.dto.chargeOff.LaoUserbillsDetailDto;


public interface UserBillsDetailService {
	
	public Map<String, Object> queryUserBillsByPage(LaoUserbillsDetailDto dto, int pageNo, int pageSize);
	
	public List<LaoUserbillsDetailDto> queryUserBills(LaoUserbillsDetailDto dto);
	
	public LaoUserbillsDetailDto getUserBillDetailByChargeId(String chargeId);
	
	public Long countTotal(Long custId, Integer cycleId, Integer operatosId);
}
