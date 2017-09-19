package com.urt.mapper;

import com.urt.po.LaoEsimImei;

public interface LaoEsimImeiMapper {
    int deleteByPrimaryKey(String imei);

    int insert(LaoEsimImei record);

    int insertSelective(LaoEsimImei record);

    LaoEsimImei selectByPrimaryKey(String imei);

    int updateByPrimaryKeySelective(LaoEsimImei record);

    int updateByPrimaryKey(LaoEsimImei record);
}