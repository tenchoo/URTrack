package com.urt.mapper;

import com.urt.po.LaoUserHisPo;

public interface LaoUserHisPoMapper {
    int deleteByPrimaryKey(Long seqId);

    int insert(LaoUserHisPo record);

    int insertSelective(LaoUserHisPo record);

    LaoUserHisPo selectByPrimaryKey(Long seqId);

    int updateByPrimaryKeySelective(LaoUserHisPo record);

    int updateByPrimaryKey(LaoUserHisPo record);
}
