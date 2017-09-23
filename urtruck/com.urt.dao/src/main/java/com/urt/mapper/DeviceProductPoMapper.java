package com.urt.mapper;

import com.urt.po.DeviceProductPo;

public interface DeviceProductPoMapper {
    int deleteByPrimaryKey(String adid);

    int insert(DeviceProductPo record);

    int insertSelective(DeviceProductPo record);

    DeviceProductPo selectByPrimaryKey(String adid);

    int updateByPrimaryKeySelective(DeviceProductPo record);

    int updateByPrimaryKey(DeviceProductPo record);
}