package com.urt.mapper;

import com.urt.po.RealNameVerify;
 
public interface RealNameVerifyMapper {
    int deleteByPrimaryKey(Short id);

    int insert(RealNameVerify record);

    int insertSelective(RealNameVerify record);

    RealNameVerify selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(RealNameVerify record);

    int updateByPrimaryKey(RealNameVerify record);
    
    RealNameVerify doQueryUnique(String userId);
}