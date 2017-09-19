package com.urt.mapper;

import com.urt.po.LaoDMPStrategyPo;

public interface LaoDMPStrategyPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPStrategyPo record);

    int insertSelective(LaoDMPStrategyPo record);

    LaoDMPStrategyPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPStrategyPo record);

    int updateByPrimaryKey(LaoDMPStrategyPo record);
}