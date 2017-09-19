package com.urt.mapper;

import java.util.List;

import com.urt.po.BBill;

public interface BBillMapper {
    int deleteByPrimaryKey(Long billId);

    int insert(BBill record);

    int insertSelective(BBill record);

    BBill selectByPrimaryKey(Long billId);

    int updateByPrimaryKeySelective(BBill record);

    int updateByPrimaryKey(BBill record);
    
    int batchInsert(List<BBill> list);
}