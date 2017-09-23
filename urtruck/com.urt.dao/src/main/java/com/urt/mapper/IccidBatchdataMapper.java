package com.urt.mapper;

import com.urt.po.IccidBatchdata;

public interface IccidBatchdataMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(IccidBatchdata record);

    int insertSelective(IccidBatchdata record);

    IccidBatchdata selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(IccidBatchdata record);

    int updateByPrimaryKey(IccidBatchdata record);
}