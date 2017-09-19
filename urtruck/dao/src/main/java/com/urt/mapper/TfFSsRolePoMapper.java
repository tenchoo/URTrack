package com.urt.mapper;

import com.urt.po.TfFSsRolePo;

public interface TfFSsRolePoMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(TfFSsRolePo record);

    int insertSelective(TfFSsRolePo record);

    TfFSsRolePo selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(TfFSsRolePo record);

    int updateByPrimaryKey(TfFSsRolePo record);
}