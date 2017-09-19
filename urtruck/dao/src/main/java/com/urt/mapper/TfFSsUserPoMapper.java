package com.urt.mapper;

import com.urt.po.TfFSsUserPo;

public interface TfFSsUserPoMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(TfFSsUserPo record);

    int insertSelective(TfFSsUserPo record);

    TfFSsUserPo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(TfFSsUserPo record);

    int updateByPrimaryKey(TfFSsUserPo record);
}