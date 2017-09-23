package com.urt.mapper;

import com.urt.po.LaoCustConcatPo;

public interface LaoCustConcatPoMapper {
    int deleteByPrimaryKey(Long contactId);

    int insert(LaoCustConcatPo record);

    int insertSelective(LaoCustConcatPo record);

    LaoCustConcatPo selectByPrimaryKey(Long contactId);

    int updateByPrimaryKeySelective(LaoCustConcatPo record);

    int updateByPrimaryKey(LaoCustConcatPo record);
}