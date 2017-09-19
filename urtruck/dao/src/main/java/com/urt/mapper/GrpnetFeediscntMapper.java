package com.urt.mapper;

import com.urt.po.GrpnetFeediscnt;

public interface GrpnetFeediscntMapper {
    int deleteByPrimaryKey(Integer feediscntId);

    int insert(GrpnetFeediscnt record);

    int insertSelective(GrpnetFeediscnt record);

    GrpnetFeediscnt selectByPrimaryKey(Integer feediscntId);

    int updateByPrimaryKeySelective(GrpnetFeediscnt record);

    int updateByPrimaryKey(GrpnetFeediscnt record);
}