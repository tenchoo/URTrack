package com.urt.mapper;

import com.urt.po.LaoDMPGroupPo;

public interface LaoDMPGroupPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPGroupPo record);

    int insertSelective(LaoDMPGroupPo record);

    LaoDMPGroupPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPGroupPo record);

    int updateByPrimaryKey(LaoDMPGroupPo record);
}