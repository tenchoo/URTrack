package com.urt.mapper;

import com.urt.po.LaoEsimUser;

public interface LaoEsimUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(LaoEsimUser record);

    int insertSelective(LaoEsimUser record);

    LaoEsimUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(LaoEsimUser record);

    int updateByPrimaryKey(LaoEsimUser record);
}