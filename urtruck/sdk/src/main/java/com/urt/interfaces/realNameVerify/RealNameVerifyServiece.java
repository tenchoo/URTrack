package com.urt.interfaces.realNameVerify;


import java.util.Map;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRealNameVerifyDto;
public interface RealNameVerifyServiece {
	/**
	 * 判断IDcode是否实名认证过
	 * @return
	 */
	public boolean checkRealName(String idnum,long accountId);
	/**
	 * 国名认证
	 * @param dto
	 * @return
	 */
	public ResultMsg customerVerifyServiece(LaoSsRealNameVerifyDto dto,LaoSsAccountDto currentUser) throws Exception;
	
	/**
	 * 获取当前用户的认证信息
	 * @param accountId
	 * @return
	 */
	public LaoSsRealNameVerifyDto getVerifyByAccountId(Long accountId);
	
	public LaoSsRealNameVerifyDto getVerifyById(Long id);
	
	public Boolean approved(Long id);
	
	public Boolean unapproved(Long id);

	public Map<String, Object> queryPage(LaoSsRealNameVerifyDto dto,Integer pageNo,Integer pageSize);
	
}
