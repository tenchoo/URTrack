package com.urt.mapper;

import com.urt.po.LaoPoolUseInfo;

public interface LaoPoolUseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoPoolUseInfo record);

    int insertSelective(LaoPoolUseInfo record);

    LaoPoolUseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoPoolUseInfo record);

    int updateByPrimaryKey(LaoPoolUseInfo record);
}