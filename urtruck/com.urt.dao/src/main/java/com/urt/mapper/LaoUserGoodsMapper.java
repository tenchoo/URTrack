package com.urt.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserGoods;

public interface LaoUserGoodsMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("goodsId") Long goodsId, @Param("startDate") Date startDate);

    int insert(LaoUserGoods record);

    int insertSelective(LaoUserGoods record);

    LaoUserGoods selectByPrimaryKey(@Param("userId") Long userId, @Param("goodsId") Long goodsId, @Param("startDate") Date startDate);

    int updateByPrimaryKeySelective(LaoUserGoods record);

    int updateByPrimaryKey(LaoUserGoods record);
}