package com.urt.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.UserInfo;
 
public interface UserInfoMapper {
    int deleteByPrimaryKey(BigDecimal id);
    
    int delete(UserInfo userInfo);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    UserInfo doQueryFirst(@Param("iccid")String iccid,@Param("userId")String userId);

    UserInfo doQueryFirstByIccid(String iccid);
    
    List<UserInfo> doQueryListByIccid(String iccid);
    
    List<UserInfo> doQueryListByApnType(String apn);
    
    List<UserInfo> doQueryListByUserId(String userId);
    
}