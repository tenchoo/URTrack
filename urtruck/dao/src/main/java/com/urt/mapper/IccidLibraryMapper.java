package com.urt.mapper;

import com.urt.po.IccidLibrary;
 
public interface IccidLibraryMapper {
    int deleteByPrimaryKey(Short id);

    int insert(IccidLibrary record);

    int insertSelective(IccidLibrary record);

    IccidLibrary selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(IccidLibrary record);

    int updateByPrimaryKey(IccidLibrary record);
    
    IccidLibrary doQueryFirst(String iccid);
}