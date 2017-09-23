package com.urt.mapper;

import com.urt.po.LaoSsResourcePo;

public interface LaoSsResourcePoMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(LaoSsResourcePo record);

    int insertSelective(LaoSsResourcePo record);

    LaoSsResourcePo selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(LaoSsResourcePo record);

    int updateByPrimaryKey(LaoSsResourcePo record);
}