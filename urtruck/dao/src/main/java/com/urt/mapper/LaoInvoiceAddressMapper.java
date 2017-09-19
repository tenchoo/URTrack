package com.urt.mapper;

import com.urt.po.LaoInvoiceAddress;

public interface LaoInvoiceAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(LaoInvoiceAddress record);

    int insertSelective(LaoInvoiceAddress record);

    LaoInvoiceAddress selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(LaoInvoiceAddress record);

    int updateByPrimaryKey(LaoInvoiceAddress record);
}