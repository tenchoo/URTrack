package com.urt.mapper;

import com.urt.po.LaoFtpfileInfo;

public interface LaoFtpfileInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoFtpfileInfo record);

    int insertSelective(LaoFtpfileInfo record);

    LaoFtpfileInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoFtpfileInfo record);

    int updateByPrimaryKey(LaoFtpfileInfo record);
}