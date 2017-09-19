package com.urt.mapper;

import com.urt.po.LaoEsimFlowGivenDetail;

public interface LaoEsimFlowGivenDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimFlowGivenDetail record);

    int insertSelective(LaoEsimFlowGivenDetail record);

    LaoEsimFlowGivenDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimFlowGivenDetail record);

    int updateByPrimaryKey(LaoEsimFlowGivenDetail record);
}