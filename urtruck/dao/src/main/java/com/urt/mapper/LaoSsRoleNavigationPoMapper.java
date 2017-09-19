package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsRoleNavigationPo;

public interface LaoSsRoleNavigationPoMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("navigationId") Long navigationId);

    int insert(LaoSsRoleNavigationPo record);

    int insertSelective(LaoSsRoleNavigationPo record);
}