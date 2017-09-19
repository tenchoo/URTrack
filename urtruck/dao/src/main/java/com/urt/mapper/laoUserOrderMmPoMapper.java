package com.urt.mapper;

import com.urt.po.laoUserOrderMmPo;

public interface laoUserOrderMmPoMapper {
    int insert(laoUserOrderMmPo record);

    int insertSelective(laoUserOrderMmPo record);
}