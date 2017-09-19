package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;

import com.urt.po.LaoRuleDef;

public interface LaoRuleDefExtMapper {
	List<LaoRuleDef> selectRuleByCustId(HashMap<String,Object> paraMap);
    
    List<LaoRuleDef> selectRules();
    
    List<LaoRuleDef> selectRulesByGroupId(HashMap<String, Object> paraMap);
    
    List<LaoRuleDef> selectRulesByGroupIdDesc(Long groupId);
    
    int countRulesByCustId(Long custId);
    
    int countRulesByGroupId(Long groupId);
    
    List<LaoRuleDef> selectRuleDefs(Long groupId);
}