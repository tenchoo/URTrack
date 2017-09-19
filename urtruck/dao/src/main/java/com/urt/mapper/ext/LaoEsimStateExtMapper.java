package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;


public interface LaoEsimStateExtMapper {
    String selectIccid(@Param("imei")String imei,@Param("operators")String operators);
}