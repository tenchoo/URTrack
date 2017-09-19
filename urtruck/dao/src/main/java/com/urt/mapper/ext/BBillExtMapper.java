package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.BBill;
import com.urt.po.BBillExt;
import com.urt.po.BBillExtPo;

public interface BBillExtMapper {
	/**
	 * 功能描述：根据账期CYCLE_ID和客户CUST_ID获取账单信息
	 * @author chenjj3
	 * @date 2016年9月20日
	 * @param cycleId,custId
	 * @return BBill
	 */
	
	List<BBill> selectByCycleId(Integer cycleId);
	List<BBillExt> selectBillItemByCycleId(Integer cycleId);
	List<BBillExt> selectBillItemByCycleIdByNumber(Map<String,Object> map);
	List<BBillExtPo> selectBillSumByCycleIdAndNumber(Map<String,Object> map);
    List<BBillExtPo> selectBillSumByUserCycleId(Integer cycleId);
    List<BBillExt> selectBillSumByItemCycleId(Integer cycleId);
    
    //获取尚未产生用户信息的集群网手机号码
    List<BBill> selectSerialNumberByUserId(Integer cycleId);
    
    int updateAllUserIdByCycleId(Integer cycleId);
}
