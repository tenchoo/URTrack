package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsRoleResourcePo;

public interface LaoSsRoleResourcePoMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);

    int insert(LaoSsRoleResourcePo record);

    int insertSelective(LaoSsRoleResourcePo record);
}