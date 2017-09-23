package com.urt.mapper;

import com.urt.po.ImeiLibrary;
 
public interface ImeiLibraryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImeiLibrary record);

    int insertSelective(ImeiLibrary record);

    ImeiLibrary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImeiLibrary record);

    int updateByPrimaryKey(ImeiLibrary record);
    
    ImeiLibrary doQueryFirst(String iccid);
}