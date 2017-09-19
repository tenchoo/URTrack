package com.urt.mapper;

import com.urt.po.LaoEsimFlowInfo;

public interface LaoEsimFlowInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimFlowInfo record);

    int insertSelective(LaoEsimFlowInfo record);

    LaoEsimFlowInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimFlowInfo record);

    int updateByPrimaryKey(LaoEsimFlowInfo record);
}