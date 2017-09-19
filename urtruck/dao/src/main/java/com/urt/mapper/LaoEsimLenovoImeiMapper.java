package com.urt.mapper;

import com.urt.po.LaoEsimLenovoImei;

public interface LaoEsimLenovoImeiMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimLenovoImei record);

    int insertSelective(LaoEsimLenovoImei record);

    LaoEsimLenovoImei selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimLenovoImei record);

    int updateByPrimaryKey(LaoEsimLenovoImei record);
}