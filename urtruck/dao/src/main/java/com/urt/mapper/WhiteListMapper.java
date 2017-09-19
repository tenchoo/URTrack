package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.WhiteList;
 
public interface WhiteListMapper {
    int insert(WhiteList record);

    int insertSelective(WhiteList record);
    
    int doQueryCount(@Param("cell")String cell,@Param("type") int type);
}