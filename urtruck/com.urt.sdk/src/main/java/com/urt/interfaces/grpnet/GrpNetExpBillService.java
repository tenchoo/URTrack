package com.urt.interfaces.grpnet;

import java.util.List;
import java.util.Map;

import com.urt.dto.grpnet.DItemDto;

public interface GrpNetExpBillService {
	
	public List<Map<String, Object>> qryBBillByCycleId(Integer cycleId);
	
	public List<Map<String, Object>> qryBBillSumByUserCycleId(Integer cycleId);
	
	public List<DItemDto> qryDItemByPItemId();
	
	public List<Map<String, Object>> qryBBillSumByItemCycleId(Integer cycleId);
	
	public List<Map<String, Object>> selectBillItemByCycleIdByNumber(Integer cycleId,String number);
	
	public Map<String, Object> selectBillSumByCycleIdAndNumber(Integer cycleId,String number);
	//处理尚未产生用户信息的集群网手机号码
	public void addGrpNetUserInfo(Integer cycleId);
}
