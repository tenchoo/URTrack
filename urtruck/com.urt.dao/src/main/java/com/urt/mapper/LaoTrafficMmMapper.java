package com.urt.mapper;

import com.urt.po.LaoTrafficMm;

public interface LaoTrafficMmMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(LaoTrafficMm record);

    int insertSelective(LaoTrafficMm record);

    LaoTrafficMm selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(LaoTrafficMm record);

    int updateByPrimaryKey(LaoTrafficMm record);
}