package com.urt.mapper;

import com.urt.po.LaoBalAlmLogPo;

public interface LaoBalAlmLogPoMapper {
    int deleteByPrimaryKey(Long alarmId);

    int insert(LaoBalAlmLogPo record);

    int insertSelective(LaoBalAlmLogPo record);

    LaoBalAlmLogPo selectByPrimaryKey(Long alarmId);

    int updateByPrimaryKeySelective(LaoBalAlmLogPo record);

    int updateByPrimaryKey(LaoBalAlmLogPo record);
}