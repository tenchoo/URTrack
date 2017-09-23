package com.urt.mapper;

import com.urt.po.LaoAlmElementPo;

public interface LaoAlmElementPoMapper {
    int deleteByPrimaryKey(Long elementId);

    int insert(LaoAlmElementPo record);

    int insertSelective(LaoAlmElementPo record);

    LaoAlmElementPo selectByPrimaryKey(Long elementId);

    int updateByPrimaryKeySelective(LaoAlmElementPo record);

    int updateByPrimaryKey(LaoAlmElementPo record);
}