package com.urt.mapper;

import com.urt.po.LaoDownloadfileConfig;

public interface LaoDownloadfileConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDownloadfileConfig record);

    int insertSelective(LaoDownloadfileConfig record);

    LaoDownloadfileConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDownloadfileConfig record);

    int updateByPrimaryKey(LaoDownloadfileConfig record);
    
}