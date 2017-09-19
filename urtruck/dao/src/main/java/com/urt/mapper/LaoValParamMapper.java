package com.urt.mapper;

import com.urt.po.LaoValParam;

public interface LaoValParamMapper {
    int deleteByPrimaryKey(Long idValue);

    int insert(LaoValParam record);

    int insertSelective(LaoValParam record);

    LaoValParam selectByPrimaryKey(Long idValue);

    int updateByPrimaryKeySelective(LaoValParam record);

    int updateByPrimaryKey(LaoValParam record);
}