package com.urt.mapper;

import com.urt.po.LaoEsimIccidLib;

public interface LaoEsimIccidLibMapper {
    int deleteByPrimaryKey(Short id);

    int insert(LaoEsimIccidLib record);

    int insertSelective(LaoEsimIccidLib record);

    LaoEsimIccidLib selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(LaoEsimIccidLib record);

    int updateByPrimaryKey(LaoEsimIccidLib record);
}