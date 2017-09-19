package com.urt.mapper;

import com.urt.po.GrpnetImpbill;

public interface GrpnetImpbillMapper {
    int deleteByPrimaryKey(Long billId);

    int insert(GrpnetImpbill record);

    int insertSelective(GrpnetImpbill record);

    GrpnetImpbill selectByPrimaryKey(Long billId);

    int updateByPrimaryKeySelective(GrpnetImpbill record);

    int updateByPrimaryKey(GrpnetImpbill record);
}