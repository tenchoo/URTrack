package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TfFSsRoleResourcePo;

public interface TfFSsRoleResourcePoMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);

    int insert(TfFSsRoleResourcePo record);

    int insertSelective(TfFSsRoleResourcePo record);
}