package com.urt.mapper;

import com.urt.po.LaoDeviceRel;

public interface LaoDeviceRelMapper {
    int deleteByPrimaryKey(Long relId);

    int insert(LaoDeviceRel record);

    int insertSelective(LaoDeviceRel record);

    LaoDeviceRel selectByPrimaryKey(Long relId);

    int updateByPrimaryKeySelective(LaoDeviceRel record);

    int updateByPrimaryKey(LaoDeviceRel record);
}