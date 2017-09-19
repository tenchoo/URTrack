package com.urt.mapper;

import com.urt.po.LaoOperatorsbillResult;

public interface LaoOperatorsbillResultMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(LaoOperatorsbillResult record);

    int insertSelective(LaoOperatorsbillResult record);

    LaoOperatorsbillResult selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(LaoOperatorsbillResult record);

    int updateByPrimaryKey(LaoOperatorsbillResult record);
}