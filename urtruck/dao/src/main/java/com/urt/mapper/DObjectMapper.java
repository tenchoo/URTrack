package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.DObject;

public interface DObjectMapper {
    int deleteByPrimaryKey(@Param("attrId") Integer attrId, @Param("attrType") String attrType, @Param("objectId") Integer objectId);

    int insert(DObject record);

    int insertSelective(DObject record);

    DObject selectByPrimaryKey(@Param("attrId") Integer attrId, @Param("attrType") String attrType, @Param("objectId") Integer objectId);

    int updateByPrimaryKeySelective(DObject record);

    int updateByPrimaryKey(DObject record);
}