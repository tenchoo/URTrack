package com.urt.mapper;

import com.urt.po.LaoDataImportLog;

public interface LaoDataImportLogMapper {
    int deleteByPrimaryKey(Long impId);

    int insert(LaoDataImportLog record);

    int insertSelective(LaoDataImportLog record);

    LaoDataImportLog selectByPrimaryKey(Long impId);

    int updateByPrimaryKeySelective(LaoDataImportLog record);

    int updateByPrimaryKey(LaoDataImportLog record);
}