package com.urt.interfaces.authority;

import java.util.List;

import com.urt.dto.LaoAccountRelDto;

public interface LaoAccountRelService {
	
	public List<LaoAccountRelDto> queryRelByRelType(LaoAccountRelDto dto);
	
	public int save(LaoAccountRelDto dto);
	
	public LaoAccountRelDto queryRelByRelAccount(String relAccount);
	
	public LaoAccountRelDto queryRelAccountByOpenId(String openId);
}
