package com.urt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserStateDd;

public interface LaoUserStateDdMapper {

	Integer selectCardCount(@Param("channelCustId") Long channelCustId,@Param("dayTime") Long dayTime);

	List<LaoUserStateDd> selectCardDetail(@Param("channelCustId") Long channelCustId,@Param("dayTime") Long dayTime);
	
	Integer selectNewTimeCard(Long channelCustId);
   
}