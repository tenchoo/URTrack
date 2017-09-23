package com.urt.mapper;

import com.urt.po.LaoEsimLog;

public interface LaoEsimLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimLog record);

    int insertSelective(LaoEsimLog record);

    LaoEsimLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimLog record);

    int updateByPrimaryKey(LaoEsimLog record);
}