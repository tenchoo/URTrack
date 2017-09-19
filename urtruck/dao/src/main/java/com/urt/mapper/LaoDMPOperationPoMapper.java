package com.urt.mapper;

import com.urt.po.LaoDMPOperationPo;

public interface LaoDMPOperationPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPOperationPo record);

    int insertSelective(LaoDMPOperationPo record);

    LaoDMPOperationPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPOperationPo record);

    int updateByPrimaryKey(LaoDMPOperationPo record);
}