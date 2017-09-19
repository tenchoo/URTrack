package com.urt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoProvideServer;

public interface LaoProvideServerMapper {
    int deleteByPrimaryKey(Long serverId);

    int insert(LaoProvideServer record);

    int insertSelective(LaoProvideServer record);

    LaoProvideServer selectByPrimaryKey(Long serverId);
    
    LaoProvideServer selectByServerName(String serverName);
    
    List<LaoProvideServer> selectServer(@Param("custid")Long custid, @Param("iccid")String iccid,@Param("serverName")String serverName);

    int updateByPrimaryKeySelective(LaoProvideServer record);

    int updateByPrimaryKey(LaoProvideServer record);
}