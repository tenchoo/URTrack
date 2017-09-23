package com.urt.interfaces.ability;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoUserIpManagerDto;


public interface LaoUserIpManagerService {
	
	public List<LaoUserIpManagerDto> getUserIpByCustId(String custId);

	 Map<String,Object> queryPage(LaoUserIpManagerDto dto, int pageNo, int pageSize);

	public Boolean delUserIP(String id);

	public Boolean stopIP(String id);

	public Boolean openIP(String id);

	public Boolean saveIp(Long custId,String Ip);
}
