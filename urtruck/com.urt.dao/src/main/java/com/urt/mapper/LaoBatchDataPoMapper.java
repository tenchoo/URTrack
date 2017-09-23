package com.urt.mapper;

import com.urt.po.LaoBatchDataPo;

public interface LaoBatchDataPoMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(LaoBatchDataPo record);

    int insertSelective(LaoBatchDataPo record);

    LaoBatchDataPo selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(LaoBatchDataPo record);

    int updateByPrimaryKey(LaoBatchDataPo record);
}