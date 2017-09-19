package com.urt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.FlowConfig;
 
public interface FlowConfigMapper {
    int deleteByPrimaryKey(Short id);

    int insert(FlowConfig record);

    int insertSelective(FlowConfig record);

    FlowConfig selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(FlowConfig record);

    int updateByPrimaryKey(FlowConfig record);
    
    FlowConfig doQueryFirst(@Param("flowSize")String flowSize,@Param("price")String price);
    
    List<FlowConfig> doQueryList();
}