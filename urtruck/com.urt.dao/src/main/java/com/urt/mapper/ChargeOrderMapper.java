package com.urt.mapper;

import com.urt.po.ChargeOrder;
 
public interface ChargeOrderMapper {
    int deleteByPrimaryKey(Short id);

    int insert(ChargeOrder record);

    int insertSelective(ChargeOrder record);

    ChargeOrder selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(ChargeOrder record);

    int updateByPrimaryKey(ChargeOrder record);
    
    ChargeOrder doQueryFirst(String orderId);
}