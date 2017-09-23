package com.urt.mapper;

import com.urt.po.LaoEsimOperatorProperty;

public interface LaoEsimOperatorPropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoEsimOperatorProperty record);

    int insertSelective(LaoEsimOperatorProperty record);

    LaoEsimOperatorProperty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaoEsimOperatorProperty record);

    int updateByPrimaryKey(LaoEsimOperatorProperty record);
  
    LaoEsimOperatorProperty selectByOperator(String operator);
}