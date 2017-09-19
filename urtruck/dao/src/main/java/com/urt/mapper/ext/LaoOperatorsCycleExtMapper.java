package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoOperatorsCycle;

public interface LaoOperatorsCycleExtMapper {

    int updateCycle(LaoOperatorsCycle record);
    LaoOperatorsCycle getOperatorsCycle(@Param("cycId")int cycId, @Param("idValue")String idValue, @Param("idType")String idType);
}