package com.urt.mapper;

import com.urt.po.LaoTrafficMMTaskPo;

public interface LaoTrafficMMTaskPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoTrafficMMTaskPo record);

    int insertSelective(LaoTrafficMMTaskPo record);

    LaoTrafficMMTaskPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoTrafficMMTaskPo record);

    int updateByPrimaryKeyWithBLOBs(LaoTrafficMMTaskPo record);

    int updateByPrimaryKey(LaoTrafficMMTaskPo record);
}