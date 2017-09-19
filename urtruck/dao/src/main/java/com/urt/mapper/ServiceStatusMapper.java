package com.urt.mapper;

import com.urt.po.ServiceStatus;

public interface ServiceStatusMapper {
    int deleteByPrimaryKey(String stateCode);

    int insert(ServiceStatus record);

    int insertSelective(ServiceStatus record);

    ServiceStatus selectByPrimaryKey(String stateCode);

    int updateByPrimaryKeySelective(ServiceStatus record);

    int updateByPrimaryKey(ServiceStatus record);
}