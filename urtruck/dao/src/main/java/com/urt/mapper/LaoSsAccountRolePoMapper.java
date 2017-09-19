package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsAccountRolePo;

public interface LaoSsAccountRolePoMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(LaoSsAccountRolePo record);

    int insertSelective(LaoSsAccountRolePo record);
}