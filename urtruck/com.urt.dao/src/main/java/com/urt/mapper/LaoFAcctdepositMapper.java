package com.urt.mapper;

import com.urt.po.LaoFAcctdeposit;

public interface LaoFAcctdepositMapper {
    int deleteByPrimaryKey(Long acctBalanceId);

    int insert(LaoFAcctdeposit record);

    int insertSelective(LaoFAcctdeposit record);

    LaoFAcctdeposit selectByPrimaryKey(Long acctBalanceId);

    int updateByPrimaryKeySelective(LaoFAcctdeposit record);

    int updateByPrimaryKey(LaoFAcctdeposit record);
}