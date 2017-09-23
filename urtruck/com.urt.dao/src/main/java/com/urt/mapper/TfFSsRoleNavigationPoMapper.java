package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TfFSsRoleNavigationPo;

public interface TfFSsRoleNavigationPoMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("navigationId") Long navigationId);

    int insert(TfFSsRoleNavigationPo record);

    int insertSelective(TfFSsRoleNavigationPo record);
}