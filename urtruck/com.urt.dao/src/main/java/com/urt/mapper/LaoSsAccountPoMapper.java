package com.urt.mapper;

import com.urt.po.LaoSsAccountPo;

public interface LaoSsAccountPoMapper {
    int deleteByPrimaryKey(Long acconutId);

    int insert(LaoSsAccountPo record);

    int insertSelective(LaoSsAccountPo record);

    LaoSsAccountPo selectByPrimaryKey(Long acconutId);

    int updateByPrimaryKeySelective(LaoSsAccountPo record);

    int updateByPrimaryKey(LaoSsAccountPo record);
}