package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;


public interface LaoTrafficMMTaskPoExtMapper {
	int getTaskCount(@Param("channelCustId")long channelCustId, @Param("type")String type, @Param("month")String month,@Param("parseInt")int parseInt,
			@Param("parseInt2")int parseInt2);

	Long getTaskId(@Param("channelCustId")long channelCustId, @Param("type")String type,@Param("month")String month, @Param("parseInt")int parseInt,
			@Param("parseInt2")int parseInt2, @Param("value1")String value1,  @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
}