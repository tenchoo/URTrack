package com.urt.mapper;

import com.urt.po.LaoFAcctdepositChange;

public interface LaoFAcctdepositChangeMapper {
    int deleteByPrimaryKey(Long accountChangeId);

    int insert(LaoFAcctdepositChange record);

    int insertSelective(LaoFAcctdepositChange record);

    LaoFAcctdepositChange selectByPrimaryKey(Long accountChangeId);

    int updateByPrimaryKeySelective(LaoFAcctdepositChange record);

    int updateByPrimaryKey(LaoFAcctdepositChange record);
}