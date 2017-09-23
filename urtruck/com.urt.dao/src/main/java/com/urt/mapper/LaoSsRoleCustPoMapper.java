package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsRoleCustPo;

public interface LaoSsRoleCustPoMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("custId") Long custId);

    int insert(LaoSsRoleCustPo record);

    int insertSelective(LaoSsRoleCustPo record);
}