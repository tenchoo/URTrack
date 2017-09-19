package com.urt.mapper;

import com.urt.po.LaoUser;

public interface LaoUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(LaoUser record);

    int insertSelective(LaoUser record);

    LaoUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(LaoUser record);

    int updateByUserId(LaoUser record);
}