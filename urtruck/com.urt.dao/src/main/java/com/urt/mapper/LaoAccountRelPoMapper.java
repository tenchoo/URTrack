package com.urt.mapper;

import com.urt.po.LaoAccountRelPo;

public interface LaoAccountRelPoMapper {
    int deleteByPrimaryKey(Long relId);

    int insert(LaoAccountRelPo record);

    int insertSelective(LaoAccountRelPo record);

    LaoAccountRelPo selectByPrimaryKey(Long relId);

    int updateByPrimaryKeySelective(LaoAccountRelPo record);

    int updateByPrimaryKey(LaoAccountRelPo record);
    
    LaoAccountRelPo selectByRelAccount(String relAccount);
}