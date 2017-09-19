package com.urt.mapper;

import com.urt.po.LaoUserbillsDetail;

public interface LaoUserbillsDetailMapper {
    int deleteByPrimaryKey(Long billDetailId);

    int insert(LaoUserbillsDetail record);

    int insertSelective(LaoUserbillsDetail record);

    LaoUserbillsDetail selectByPrimaryKey(Long billDetailId);

    int updateByPrimaryKeySelective(LaoUserbillsDetail record);

    int updateByPrimaryKey(LaoUserbillsDetail record);
}