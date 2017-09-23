package com.urt.mapper;

import com.urt.po.IccidLog;
 
public interface IccidLogMapper {
    int insert(IccidLog record);

    int insertSelective(IccidLog record);
}