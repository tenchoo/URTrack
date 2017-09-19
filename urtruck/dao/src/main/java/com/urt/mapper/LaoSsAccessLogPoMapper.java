package com.urt.mapper;

import com.urt.po.LaoSsAccessLogPo;

public interface LaoSsAccessLogPoMapper {
    int insert(LaoSsAccessLogPo record);

    int insertSelective(LaoSsAccessLogPo record);
}