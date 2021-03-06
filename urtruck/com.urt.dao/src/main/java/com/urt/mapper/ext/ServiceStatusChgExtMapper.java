package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.ServiceStatusChg;

public interface ServiceStatusChgExtMapper {
    
    ServiceStatusChg selectByTradeTypeCode(@Param("tradeTypeCode")String tradeTypeCode, @Param("oldStateCode")String oldStateCode);
    public List<String> getTypeCode();

}