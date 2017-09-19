package com.urt.mapper;

import com.urt.po.ServiceStatusChg;

public interface ServiceStatusChgMapper {
    int deleteByPrimaryKey(Short id);

    int insert(ServiceStatusChg record);

    int insertSelective(ServiceStatusChg record);

    ServiceStatusChg selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(ServiceStatusChg record);

    int updateByPrimaryKey(ServiceStatusChg record);
}