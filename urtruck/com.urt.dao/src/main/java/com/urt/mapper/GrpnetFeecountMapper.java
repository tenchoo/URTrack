package com.urt.mapper;

import com.urt.po.GrpnetFeecount;

public interface GrpnetFeecountMapper {
    int deleteByPrimaryKey(Integer feecountId);

    int insert(GrpnetFeecount record);

    int insertSelective(GrpnetFeecount record);

    GrpnetFeecount selectByPrimaryKey(Integer feecountId);

    int updateByPrimaryKeySelective(GrpnetFeecount record);

    int updateByPrimaryKey(GrpnetFeecount record);
}