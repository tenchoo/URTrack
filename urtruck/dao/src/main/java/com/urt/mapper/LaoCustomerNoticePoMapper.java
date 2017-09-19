package com.urt.mapper;

import com.urt.po.LaoCustomerNoticePo;

public interface LaoCustomerNoticePoMapper {
    int deleteByPrimaryKey(Long noticeid);

    int insert(LaoCustomerNoticePo record);

    int insertSelective(LaoCustomerNoticePo record);

    LaoCustomerNoticePo selectByPrimaryKey(Long noticeid);

    int updateByPrimaryKeySelective(LaoCustomerNoticePo record);

    int updateByPrimaryKey(LaoCustomerNoticePo record);
}