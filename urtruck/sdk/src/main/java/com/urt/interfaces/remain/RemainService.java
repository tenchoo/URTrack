package com.urt.interfaces.remain;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.Remain.BalAlarmDto;
import com.urt.dto.Remain.LaoFAcctdepositDto;
import com.urt.dto.Remain.LaoRuleDefDto;

public interface RemainService {
	List<LaoCustGroupDto> selectAll();
	
	LaoFAcctdepositDto selectdepositByCustId(Long channelCustId);
	
	List<LaoRuleDefDto>selectRuleByCustId(HashMap<String,Object> paramMap);

	int countUsersByCustId(Long custId);
	
	int selectRecvFeeByPayTag(Map<String,Object> paramMap);

	int selectNeedReturn(Map<String, Object> paramMap);

	int selectReturned(Map<String, Object> paramMap);

	List<LaoRuleDefDto> selectRules();

	List<LaoRuleDefDto> selectRulesByGroupId(HashMap<String, Object> paraMap);
	
	int saveRules(String param) throws ParseException;

	int payment(String param, Long custId);

	int getRoleIdByAccountId(Long accountId);

	Map<String, Object> getAccessList(Long custId, String cyc, int start, int end);

	Map<String, Object> getPayLogList(Long custId, String cyc, int start, int end);

	Map<String, Object> getBillResList(Long custId, String cyc, int start, int end);

	List<Map<String, Object>> selectComCustByName(String param);

	List<HashMap<String, Object>> queryBillResByCustId(HashMap<String, Object> paraMap);

	void returnFee(Long balanceId, String curId);

	int coutBillResByCustId(HashMap<String, Object> paraMap);

	/**
	 * 扣费接口
	 * @param int
	 * @return 0:成功 1:失败
	 */
	int charge(HashMap<String, Object> paraMap);

	HashMap<String, Object> generateCyc(String cyc);

	int countRulesByCustId(Long custId);

	int countRulesByGroupId(Long groupId);
	
	// 余额中心抽出接口供其他模块调用
	int paymentOut(String fee,Long custId,Long accId );
	
	// 更新结算规则
	int updateRules(String param) throws ParseException;
	//设置余额告警规则
	int saveBalRules(String param) throws ParseException ;
	
	BalAlarmDto getBalRules(Long custId) throws ParseException;
	Long delBalRules(Long custId) throws ParseException;
}
