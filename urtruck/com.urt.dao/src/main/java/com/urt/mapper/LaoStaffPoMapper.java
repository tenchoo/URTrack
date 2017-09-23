package com.urt.mapper;

import com.urt.po.LaoStaffPo;

public interface LaoStaffPoMapper {
    int insert(LaoStaffPo record);

    int insertSelective(LaoStaffPo record);
}