package com.urt.mapper;

import com.urt.po.LaoSmsTmpl;

public interface LaoSmsTmplMapper {
    int deleteByPrimaryKey(Integer templeteId);

    int insert(LaoSmsTmpl record);

    int insertSelective(LaoSmsTmpl record);

    LaoSmsTmpl selectByPrimaryKey(Integer templeteId);

    int updateByPrimaryKeySelective(LaoSmsTmpl record);

    int updateByPrimaryKey(LaoSmsTmpl record);
}