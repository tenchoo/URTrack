package com.urt.mapper;

import com.urt.po.LaoOperatorsCycle;

public interface LaoOperatorsCycleMapper {
    int deleteByPrimaryKey(Integer cycId);

    int insert(LaoOperatorsCycle record);

    int insertSelective(LaoOperatorsCycle record);

    LaoOperatorsCycle selectByPrimaryKey(Integer cycId);

    int updateByPrimaryKeySelective(LaoOperatorsCycle record);

    int updateByPrimaryKey(LaoOperatorsCycle record);
}