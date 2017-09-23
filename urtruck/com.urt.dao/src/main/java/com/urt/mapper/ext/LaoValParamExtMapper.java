package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoValParam;

public interface LaoValParamExtMapper {
    List<LaoValParam> selectAllParamsByRuleId(Long ruleId);
}