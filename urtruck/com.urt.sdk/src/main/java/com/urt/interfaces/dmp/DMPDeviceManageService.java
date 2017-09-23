package com.urt.interfaces.dmp;

import java.util.Map;

import com.urt.dto.dmp.LaoDMPGroupDto;

public interface DMPDeviceManageService {
	Map<String, Object> queryPage(LaoDMPGroupDto dto, int pageNo,
			int pageSize);
	Integer delDeviceManage(Long id);
	Integer saveDeviceManage(LaoDMPGroupDto dto);
	LaoDMPGroupDto getDeviceManageById(Long manageId);
	Integer updateManage(LaoDMPGroupDto dto);
}
