package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.OperatorPlan;

public interface OperatorPlanExtMapper {
    
    List<OperatorPlan> findOperatorPlan(OperatorPlan po);
    
    OperatorPlan queryByOperatorsPid(String operatorsPid);
    
    OperatorPlan queryByPlanName(String planName);
    
    List<Map<String,Object>> queryForNotSend(String iccid);
    
}