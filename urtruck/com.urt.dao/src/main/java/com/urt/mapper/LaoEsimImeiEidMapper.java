package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimImeiEid;

public interface LaoEsimImeiEidMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimImeiEid record);

    int insertSelective(LaoEsimImeiEid record);

    LaoEsimImeiEid selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimImeiEid record);

    int updateByPrimaryKey(LaoEsimImeiEid record);

	int updateByEid(@Param(value="eid")String eid, @Param(value="imeiid")String imeiid);
}