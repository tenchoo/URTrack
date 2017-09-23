package com.urt.mapper;

import com.urt.po.LaoReptInfoPo;

public interface LaoReptInfoPoMapper {
    int deleteByPrimaryKey(Long reptId);

    int insert(LaoReptInfoPo record);

    int insertSelective(LaoReptInfoPo record);

    LaoReptInfoPo selectByPrimaryKey(Long reptId);

    int updateByPrimaryKeySelective(LaoReptInfoPo record);

    int updateByPrimaryKey(LaoReptInfoPo record);
}