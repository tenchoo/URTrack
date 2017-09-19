package com.urt.mapper;

import com.urt.po.LaoSsLoginLogPo;

public interface LaoSsLoginLogPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoSsLoginLogPo record);

    int insertSelective(LaoSsLoginLogPo record);

    LaoSsLoginLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoSsLoginLogPo record);

    int updateByPrimaryKey(LaoSsLoginLogPo record);
}