package com.urt.mapper;

import com.urt.po.LaoOperatorsBill;

public interface LaoOperatorsBillMapper {
    int deleteByPrimaryKey(Long operatorsBillId);

    int insert(LaoOperatorsBill record);

    int insertSelective(LaoOperatorsBill record);

    LaoOperatorsBill selectByPrimaryKey(Long operatorsBillId);

    int updateByPrimaryKeySelective(LaoOperatorsBill record);

    int updateByPrimaryKey(LaoOperatorsBill record);
}