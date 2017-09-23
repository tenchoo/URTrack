package com.urt.mapper;

import com.urt.po.ApiMsgLog;

public interface ApiMsgLogMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(ApiMsgLog record);

    int insertSelective(ApiMsgLog record);

    ApiMsgLog selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ApiMsgLog record);

    int updateByPrimaryKey(ApiMsgLog record);
}