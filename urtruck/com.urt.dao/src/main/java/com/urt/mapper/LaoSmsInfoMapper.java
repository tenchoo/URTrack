package com.urt.mapper;

import com.urt.po.LaoSmsInfo;

public interface LaoSmsInfoMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(LaoSmsInfo record);

    int insertSelective(LaoSmsInfo record);

    LaoSmsInfo selectByPrimaryKey(Long smsId);

    int updateByPrimaryKeySelective(LaoSmsInfo record);

    int updateByPrimaryKey(LaoSmsInfo record);
}