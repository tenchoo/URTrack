package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoAlmRuleElemPo;


public interface LaoAlmRuleElemPoExtMapper {
    List<Map<String, Object>> getElementsByRuleType(LaoAlmRuleElemPo po); 
}