package com.urt.mapper;

import com.urt.po.LaoPool;

public interface LaoPoolMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoPool record);

    int insertSelective(LaoPool record);

    LaoPool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoPool record);

    int updateByPrimaryKey(LaoPool record);
}