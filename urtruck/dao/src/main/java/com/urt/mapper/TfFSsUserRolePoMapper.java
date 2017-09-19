package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TfFSsUserRolePo;

public interface TfFSsUserRolePoMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(TfFSsUserRolePo record);

    int insertSelective(TfFSsUserRolePo record);
}