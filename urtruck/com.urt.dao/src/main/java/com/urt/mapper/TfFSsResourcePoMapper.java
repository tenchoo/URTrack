package com.urt.mapper;

import com.urt.po.TfFSsResourcePo;

public interface TfFSsResourcePoMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(TfFSsResourcePo record);

    int insertSelective(TfFSsResourcePo record);

    TfFSsResourcePo selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(TfFSsResourcePo record);

    int updateByPrimaryKey(TfFSsResourcePo record);
}