package com.urt.mapper;

import com.urt.po.LaoPropertiesConfig;

public interface LaoPropertiesConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoPropertiesConfig record);

    int insertSelective(LaoPropertiesConfig record);

    LaoPropertiesConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoPropertiesConfig record);

    int updateByPrimaryKey(LaoPropertiesConfig record);
}