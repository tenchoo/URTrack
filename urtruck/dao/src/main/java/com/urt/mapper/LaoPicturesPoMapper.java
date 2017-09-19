package com.urt.mapper;

import com.urt.po.LaoPicturesPo;

public interface LaoPicturesPoMapper {
    int deleteByPrimaryKey(Long picId);

    int insert(LaoPicturesPo record);

    int insertSelective(LaoPicturesPo record);

    LaoPicturesPo selectByPrimaryKey(Long picId);

    int updateByPrimaryKeySelective(LaoPicturesPo record);

    int updateByPrimaryKeyWithBLOBs(LaoPicturesPo record);

    int updateByPrimaryKey(LaoPicturesPo record);
}