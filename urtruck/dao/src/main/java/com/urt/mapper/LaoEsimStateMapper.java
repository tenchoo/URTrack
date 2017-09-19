package com.urt.mapper;

import com.urt.po.LaoEsimState;

public interface LaoEsimStateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimState record);

    int insertSelective(LaoEsimState record);

    LaoEsimState selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimState record);

    int updateByPrimaryKey(LaoEsimState record);
}