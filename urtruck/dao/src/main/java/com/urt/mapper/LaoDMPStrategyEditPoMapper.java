package com.urt.mapper;

import com.urt.po.LaoDMPStrategyEditPo;

public interface LaoDMPStrategyEditPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPStrategyEditPo record);

    int insertSelective(LaoDMPStrategyEditPo record);

    LaoDMPStrategyEditPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPStrategyEditPo record);

    int updateByPrimaryKey(LaoDMPStrategyEditPo record);
}