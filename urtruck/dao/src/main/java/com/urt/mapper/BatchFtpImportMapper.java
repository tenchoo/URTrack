package com.urt.mapper;

import com.urt.po.BatchFtpImport;

public interface BatchFtpImportMapper {
    int deleteByPrimaryKey(Short tradeTypeCode);

    int insert(BatchFtpImport record);

    int insertSelective(BatchFtpImport record);

    BatchFtpImport selectByPrimaryKey(Short tradeTypeCode);

    int updateByPrimaryKeySelective(BatchFtpImport record);

    int updateByPrimaryKey(BatchFtpImport record);
}