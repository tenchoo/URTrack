package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserRes;

public interface LaoUserResMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("resTypeCode") String resTypeCode, @Param("resCode") String resCode, @Param("startDate") Date startDate);

    int insert(LaoUserRes record);

    int insertSelective(LaoUserRes record);

    LaoUserRes selectByPrimaryKey(@Param("userId") Long userId, @Param("resTypeCode") String resTypeCode, @Param("resCode") String resCode, @Param("startDate") Date startDate);

    int updateByPrimaryKeySelective(LaoUserRes record);

    int updateByPrimaryKey(LaoUserRes record);
}