package com.urt.interfaces.alarm;

import java.util.List;
import java.util.Map;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoAlmElemValueDto;
import com.urt.dto.LaoAlmRuleDto;
import com.urt.dto.LaoAlmRuleElemDto;
import com.urt.dto.LaoAlmRuleLogDto;
import com.urt.dto.LaoAlmRuleTypeDto;
import com.urt.dto.LaoSsAccountDto;

public interface AlarmService {
	public Map<String, Object> queryPage(LaoAlmRuleDto dto,Integer pageNo,Integer pageSize);
	
	public Map<String, Object> queryLogPage(LaoAlmRuleLogDto almRuleLogDto,int pageNo, int pageSize);
	
	public List<Map<String, Object>> getLevel1();
	
	public List<Map<String, Object>> getLevel2(Integer pid);
	
	public Integer delRule(Long id);
	
	public Integer updateRuleStatus(Long id,String status);
	
	public LaoAlmRuleTypeDto getParentAlmRuleType(Long ruleTypeId);
	
	public List<Map<String, Object>> getElementsByRuleType(LaoAlmRuleElemDto dto);
	
	public Integer saveRule(LaoAlmRuleDto dto);
	
	public Integer updateRule(LaoAlmRuleDto dto);
	
	public Integer saveRuleElemVaue(LaoAlmElemValueDto dto);
	
	public LaoAlmRuleDto getRuleById(Long ruleId);
	
	public List<LaoAlmElemValueDto> getRuleElemVaue(Long ruleId);
	
	public Integer delAllElemValueByRuleId(Long ruleId);
	
	public String sendMsg(LaoSsAccountDto currentUser);

	public List<Map<String, Object>> queryCardInfo(String almId);

	public String sendMsgByCust(LaoSsAccountDto currentUser);
}
