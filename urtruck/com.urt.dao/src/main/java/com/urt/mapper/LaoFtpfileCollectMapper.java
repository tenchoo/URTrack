package com.urt.mapper;

import com.urt.po.LaoFtpfileCollect;

public interface LaoFtpfileCollectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoFtpfileCollect record);

    int insertSelective(LaoFtpfileCollect record);

    LaoFtpfileCollect selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoFtpfileCollect record);

    int updateByPrimaryKey(LaoFtpfileCollect record);
}