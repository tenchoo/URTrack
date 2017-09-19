package com.urt.mapper;

import com.urt.po.LaoEsimDetail;

public interface LaoEsimDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimDetail record);

    int insertSelective(LaoEsimDetail record);

    LaoEsimDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimDetail record);

    int updateByPrimaryKey(LaoEsimDetail record);
}