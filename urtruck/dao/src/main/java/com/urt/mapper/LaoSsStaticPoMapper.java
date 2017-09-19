package com.urt.mapper;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoSsStaticPo;

public interface LaoSsStaticPoMapper {
    int deleteByPrimaryKey(Integer staticId);

    int insert(LaoSsStaticPo record);

    int insertSelective(LaoSsStaticPo record);

    LaoSsStaticPo selectByPrimaryKey(Integer staticId);
    
    List<Map> queryStatic(LaoSsStaticPo record); 
    
    LaoSsStaticPo getStaticByCustIdCode(LaoSsStaticPo record); 

    int updateByPrimaryKeySelective(LaoSsStaticPo record);

    int updateByPrimaryKey(LaoSsStaticPo record);
    
    List<Map> queryStaticByClo(LaoSsStaticPo record);
    
    List<Map> queryStaticByCode(LaoSsStaticPo record);
    
    List<Map> queryStaticByParent(LaoSsStaticPo record);
    
    List<Map> queryProductTypeByCustId(String custId);
    
    List<Map> queryProductVersionByPid(LaoSsStaticPo po);
}