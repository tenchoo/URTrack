package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeSvcState;

public interface TradeSvcStateMapper {
    int deleteByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("serviceId") Integer serviceId, @Param("stateCode") String stateCode, @Param("modifyTag") String modifyTag);

    int insert(TradeSvcState record);

    int insertSelective(TradeSvcState record);

    TradeSvcState selectByPrimaryKey(@Param("tradeId") Long tradeId, @Param("acceptMonth") Short acceptMonth, @Param("userId") Long userId, @Param("serviceId") Integer serviceId, @Param("stateCode") String stateCode, @Param("modifyTag") String modifyTag);

    int updateByPrimaryKeySelective(TradeSvcState record);

    int updateByPrimaryKey(TradeSvcState record);
}