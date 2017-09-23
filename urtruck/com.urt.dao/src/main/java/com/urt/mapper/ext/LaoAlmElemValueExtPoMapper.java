package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoAlmElemValuePo;


public interface LaoAlmElemValueExtPoMapper {
    int deletesByRuleId(Long ruleId);
    
    List<LaoAlmElemValuePo> getElemValuesByRuleId(Long ruleId);
    
    

}