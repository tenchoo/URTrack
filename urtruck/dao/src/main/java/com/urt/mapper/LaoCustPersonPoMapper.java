package com.urt.mapper;

import com.urt.po.LaoCustPersonPo;

public interface LaoCustPersonPoMapper {
    int deleteByPrimaryKey(Long custId);

    int insert(LaoCustPersonPo record);

    int insertSelective(LaoCustPersonPo record);

    LaoCustPersonPo selectByPrimaryKey(Long custId);

    int updateByPrimaryKeySelective(LaoCustPersonPo record);

    int updateByPrimaryKey(LaoCustPersonPo record);
}