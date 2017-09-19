package com.urt.mapper;

import com.urt.po.LaoPoolMember;

public interface LaoPoolMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoPoolMember record);

    int insertSelective(LaoPoolMember record);

    LaoPoolMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoPoolMember record);

    int updateByPrimaryKey(LaoPoolMember record);
}