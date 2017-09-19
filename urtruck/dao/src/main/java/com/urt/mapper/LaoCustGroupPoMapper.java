package com.urt.mapper;

import com.urt.po.LaoCustGroupPo;

public interface LaoCustGroupPoMapper {
    int deleteByPrimaryKey(Long custId);

    int insert(LaoCustGroupPo record);

    int insertSelective(LaoCustGroupPo record);

    LaoCustGroupPo selectByPrimaryKey(Long custId);

    int updateByPrimaryKeySelective(LaoCustGroupPo record);

    int updateByPrimaryKey(LaoCustGroupPo record);
}