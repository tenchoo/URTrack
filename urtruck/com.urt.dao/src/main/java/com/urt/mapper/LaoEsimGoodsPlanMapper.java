package com.urt.mapper;

import com.urt.po.LaoEsimGoodsPlan;

public interface LaoEsimGoodsPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimGoodsPlan record);

    int insertSelective(LaoEsimGoodsPlan record);

    LaoEsimGoodsPlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimGoodsPlan record);

    int updateByPrimaryKey(LaoEsimGoodsPlan record);
}