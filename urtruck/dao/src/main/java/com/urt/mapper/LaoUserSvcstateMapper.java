package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserSvcstate;

public interface LaoUserSvcstateMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("serviceId") Integer serviceId, @Param("startDate") Date startDate);

    int insert(LaoUserSvcstate record);

    int insertSelective(LaoUserSvcstate record);

    LaoUserSvcstate selectByPrimaryKey(@Param("userId") Long userId, @Param("serviceId") Integer serviceId, @Param("startDate") Date startDate);
    
    LaoUserSvcstate selectByUserId(Long userId);

    int updateByPrimaryKeySelective(LaoUserSvcstate record);

    int updateByPrimaryKey(LaoUserSvcstate record);
}