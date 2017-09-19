package com.urt.mapper;

import com.urt.po.LaoDCommparaPo;

public interface LaoDCommparaPoMapper {
    int deleteByPrimaryKey(Long paraId);

    int insert(LaoDCommparaPo record);

    int insertSelective(LaoDCommparaPo record);

    LaoDCommparaPo selectByPrimaryKey(Long paraId);

    int updateByPrimaryKeySelective(LaoDCommparaPo record);

    int updateByPrimaryKey(LaoDCommparaPo record);
}