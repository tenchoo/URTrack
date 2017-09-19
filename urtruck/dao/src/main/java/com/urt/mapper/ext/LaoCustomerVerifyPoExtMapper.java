package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoCustomerVerifyPo;

public interface LaoCustomerVerifyPoExtMapper {
	int getCountByIdcode(String idnum);

	LaoCustomerVerifyPo getVerifyById(Long id);
	
	LaoCustomerVerifyPo getFailMessage(Long accountId);
	
	int updateFailTimes(LaoCustomerVerifyPo po);
	
	int updateStatus(LaoCustomerVerifyPo po);
	
	LaoCustomerVerifyPo getVerifyByAccountId(@Param("accountId")Long accountId);
	
	List<LaoCustomerVerifyPo> queryPage(Page<LaoCustomerVerifyPo> page);
	
}
