package com.urt.mapper;

import com.urt.po.LaoDMPCardPo;

public interface LaoDMPCardPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoDMPCardPo record);

    int insertSelective(LaoDMPCardPo record);

    LaoDMPCardPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoDMPCardPo record);

    int updateByPrimaryKey(LaoDMPCardPo record);
}