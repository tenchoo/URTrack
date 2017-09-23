package com.urt.mapper;

import com.urt.po.UserOperatorInfoPO;

public interface UserOperatorInfoPOMapper {
    int insert(UserOperatorInfoPO record);

    int insertSelective(UserOperatorInfoPO record);
}