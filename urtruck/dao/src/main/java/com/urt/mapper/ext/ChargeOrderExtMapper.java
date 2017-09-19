package com.urt.mapper.ext;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.ChargeOrder;
 
public interface ChargeOrderExtMapper {
  
	//分页查询充值记录
    List<ChargeOrder> doQueryList(@Param("userId")String userId, @Param("startRow")int startRow,@Param("endRow")int endRow, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
    //查询总共记录条数
    int doQueryCount(@Param("userId")String userId,@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}