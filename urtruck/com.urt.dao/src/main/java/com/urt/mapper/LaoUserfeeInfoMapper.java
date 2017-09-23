package com.urt.mapper;

import com.urt.po.LaoUserfeeInfo;

public interface LaoUserfeeInfoMapper {
    int deleteByPrimaryKey(Long chargeId);

    int insert(LaoUserfeeInfo record);

    int insertSelective(LaoUserfeeInfo record);

    LaoUserfeeInfo selectByPrimaryKey(Long chargeId);

    int updateByPrimaryKeySelective(LaoUserfeeInfo record);

    int updateByPrimaryKey(LaoUserfeeInfo record);
}