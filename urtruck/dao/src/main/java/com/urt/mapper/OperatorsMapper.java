package com.urt.mapper;

import com.urt.po.Operators;

public interface OperatorsMapper {
    int deleteByPrimaryKey(Integer operatorsId);

    int insert(Operators record);

    int insertSelective(Operators record);

    Operators selectByPrimaryKey(Integer operatorsId);

    int updateByPrimaryKeySelective(Operators record);

    int updateByPrimaryKey(Operators record);
}