package com.urt.mapper;

import com.urt.po.LaoWebDesign;

public interface LaoWebDesignMapper {
    int deleteByPrimaryKey(Long designId);

    int insert(LaoWebDesign record);

    int insertSelective(LaoWebDesign record);

    LaoWebDesign selectByPrimaryKey(Long designId);

    int updateByPrimaryKeySelective(LaoWebDesign record);

    int updateByPrimaryKey(LaoWebDesign record);
}