package com.urt.mapper;

import com.urt.po.LaoBillResultHis;

public interface LaoBillResultHisMapper {
    int deleteByPrimaryKey(Long balanceId);

    int insert(LaoBillResultHis record);

    int insertSelective(LaoBillResultHis record);

    LaoBillResultHis selectByPrimaryKey(Long balanceId);

    int updateByPrimaryKeySelective(LaoBillResultHis record);

    int updateByPrimaryKey(LaoBillResultHis record);
}