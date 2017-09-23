package com.urt.mapper;

import com.urt.po.LaoExcpDef;

public interface LaoExcpDefMapper {
    int deleteByPrimaryKey(Short excpTypeCode);

    int insert(LaoExcpDef record);

    int insertSelective(LaoExcpDef record);

    LaoExcpDef selectByPrimaryKey(Short excpTypeCode);

    int updateByPrimaryKeySelective(LaoExcpDef record);

    int updateByPrimaryKey(LaoExcpDef record);
}