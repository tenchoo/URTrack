package com.urt.mapper;

import java.util.List;

import com.urt.po.LaoSsRolePo;

public interface LaoSsRolePoMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(LaoSsRolePo record);

    int insertSelective(LaoSsRolePo record);

    LaoSsRolePo selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(LaoSsRolePo record);

    int updateByPrimaryKey(LaoSsRolePo record);
    
    List<LaoSsRolePo> selectLowPriorityRole(Long priority);
}