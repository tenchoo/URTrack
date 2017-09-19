package com.urt.mapper;

import com.urt.po.LaoDMPLogPo;

public interface LaoDMPLogPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPLogPo record);

    int insertSelective(LaoDMPLogPo record);

    LaoDMPLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPLogPo record);

    int updateByPrimaryKey(LaoDMPLogPo record);
}