package com.urt.mapper;

import com.urt.po.LaoInvoiceLog;

public interface LaoInvoiceLogMapper {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(LaoInvoiceLog record);

    int insertSelective(LaoInvoiceLog record);

    LaoInvoiceLog selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(LaoInvoiceLog record);

    int updateByPrimaryKey(LaoInvoiceLog record);
}