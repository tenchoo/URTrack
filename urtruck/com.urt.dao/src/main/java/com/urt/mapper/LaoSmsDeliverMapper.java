package com.urt.mapper;

import java.util.List;

import com.urt.po.LaoSmsDeliver;

public interface LaoSmsDeliverMapper {
    int insert(LaoSmsDeliver record);

    int insertSelective(LaoSmsDeliver record);
    
    List<LaoSmsDeliver> selectByPrimaryKey(String custId,String startDate,String endDate);

    void updateByPrimaryKeySelective(LaoSmsDeliver laoSmsDeliverPo);
}