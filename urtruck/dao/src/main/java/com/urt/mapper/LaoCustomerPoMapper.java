package com.urt.mapper;

import java.util.List;

import com.urt.po.LaoCustomerPo;

public interface LaoCustomerPoMapper {
    int deleteByPrimaryKey(Long custId);

    int insert(LaoCustomerPo record);

    int insertSelective(LaoCustomerPo record);

    LaoCustomerPo selectByPrimaryKey(Long custId);

    int updateByPrimaryKeySelective(LaoCustomerPo record);

    int updateByPrimaryKey(LaoCustomerPo record);
    
    /**
     * 查询下级客户列表（包含自身）
     * @param custId
     * @return
     */
    List<LaoCustomerPo> selectChildCustList(Long custId);
}