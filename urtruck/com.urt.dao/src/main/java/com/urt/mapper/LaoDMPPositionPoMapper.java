package com.urt.mapper;

import com.urt.po.LaoDMPPositionPo;

public interface LaoDMPPositionPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPPositionPo record);

    int insertSelective(LaoDMPPositionPo record);

    LaoDMPPositionPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPPositionPo record);

    int updateByPrimaryKey(LaoDMPPositionPo record);
}