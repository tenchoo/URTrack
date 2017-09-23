package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserIpManager;

public interface LaoUserIpManagerMapper {
	int insert(LaoUserIpManager record);

    int insertSelective(LaoUserIpManager record);
    
    int doQueryIP(@Param("custId")Long custId,@Param("ipAddress")String ipAddress);
    
    int doQueryIPIsExist(Long custId);
    
    int updateByIp(LaoUserIpManager record);
    
    int deleteUserIP(Long id);

	int updateStop(Long id);
	
	int updateOpen(Long id);
}