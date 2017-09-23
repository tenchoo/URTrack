package com.urt.mapper;

import com.urt.po.LaoTrafficDetail;

public interface LaoTrafficDetailMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(LaoTrafficDetail record);

    int insertSelective(LaoTrafficDetail record);

    LaoTrafficDetail selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(LaoTrafficDetail record);

    int updateByPrimaryKey(LaoTrafficDetail record);
}