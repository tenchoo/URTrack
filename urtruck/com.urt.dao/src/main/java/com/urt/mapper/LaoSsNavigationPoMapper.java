package com.urt.mapper;

import com.urt.po.LaoSsNavigationPo;

public interface LaoSsNavigationPoMapper {
    int deleteByPrimaryKey(Long navigationId);

    int insert(LaoSsNavigationPo record);

    int insertSelective(LaoSsNavigationPo record);

    LaoSsNavigationPo selectByPrimaryKey(Long navigationId);

    int updateByPrimaryKeySelective(LaoSsNavigationPo record);

    int updateByPrimaryKey(LaoSsNavigationPo record);
}