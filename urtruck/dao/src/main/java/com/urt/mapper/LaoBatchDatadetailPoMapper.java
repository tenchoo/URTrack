package com.urt.mapper;

import com.urt.po.LaoBatchDatadetailPo;

public interface LaoBatchDatadetailPoMapper {
    int deleteByPrimaryKey(Long datadetailId);

    int insert(LaoBatchDatadetailPo record);

    int insertSelective(LaoBatchDatadetailPo record);

    LaoBatchDatadetailPo selectByPrimaryKey(Long datadetailId);

    int updateByPrimaryKeySelective(LaoBatchDatadetailPo record);

    int updateByPrimaryKey(LaoBatchDatadetailPo record);
}