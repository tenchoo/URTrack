package com.urt.mapper.ext;


import com.urt.po.LaoFAcctdeposit;

public interface LaoFAcctdepositExtMapper {
	
	LaoFAcctdeposit selectByCustId(Long channelCustId);
	
	int countUsersByCustId(Long channelCustId);
	int updateByCustId(LaoFAcctdeposit record);
	int updateByPrimaryKey(LaoFAcctdeposit record);
}