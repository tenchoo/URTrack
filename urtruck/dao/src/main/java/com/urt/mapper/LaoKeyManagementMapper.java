package com.urt.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoKeyManagement;

public interface LaoKeyManagementMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(LaoKeyManagement record);

    int insertSelective(LaoKeyManagement record);

    LaoKeyManagement selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(LaoKeyManagement record);

    int updateByPrimaryKey(LaoKeyManagement record);
    
    int doQueryAppKey(@Param("custId")Long custId,@Param("authKey")String authKey);
    
    int doQueryAppKeyIsExist(String custId);
    
    LaoKeyManagement selectByCustId(String custId);

	List<Map<String, Object>> pageQuery(Map<String, Object> param);

	int totalCount(@Param("custName")String custName);


}