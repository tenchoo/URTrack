package com.urt.mapper;

import com.urt.po.TfFSsNavigationPo;

public interface TfFSsNavigationPoMapper {
    int deleteByPrimaryKey(Long navigationId);

    int insert(TfFSsNavigationPo record);

    int insertSelective(TfFSsNavigationPo record);

    TfFSsNavigationPo selectByPrimaryKey(Long navigationId);

    int updateByPrimaryKeySelective(TfFSsNavigationPo record);

    int updateByPrimaryKey(TfFSsNavigationPo record);
}