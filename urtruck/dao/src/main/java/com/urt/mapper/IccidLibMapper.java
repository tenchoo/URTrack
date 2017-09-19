package com.urt.mapper;

import com.urt.po.IccidLib;

public interface IccidLibMapper {
    int deleteByPrimaryKey(Short id);

    int insert(IccidLib record);

    int insertSelective(IccidLib record);

    IccidLib selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(IccidLib record);

    int updateByPrimaryKey(IccidLib record);
    
}