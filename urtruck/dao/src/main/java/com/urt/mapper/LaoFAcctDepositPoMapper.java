package com.urt.mapper;

import com.urt.po.LaoFAcctDepositPo;

public interface LaoFAcctDepositPoMapper {
    int deleteByPrimaryKey(Long acctBalanceId);

    int insert(LaoFAcctDepositPo record);

    int insertSelective(LaoFAcctDepositPo record);

    LaoFAcctDepositPo selectByPrimaryKey(Long acctBalanceId);

    int updateByPrimaryKeySelective(LaoFAcctDepositPo record);

    int updateByPrimaryKey(LaoFAcctDepositPo record);
}