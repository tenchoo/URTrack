package com.urt.mapper;

import com.urt.po.LaoCustConfig;

public interface LaoCustConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoCustConfig record);

    int insertSelective(LaoCustConfig record);

    LaoCustConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoCustConfig record);

    int updateByPrimaryKey(LaoCustConfig record);
}