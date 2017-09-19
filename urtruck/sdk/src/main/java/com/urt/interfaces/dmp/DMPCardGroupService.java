package com.urt.interfaces.dmp;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.dto.dmp.LaoDMPCardGroupDto;

public interface DMPCardGroupService {
	int batchDevice(List<LaoDMPCardGroupDto> list);
	LaoDMPCardGroupDto  selectByIm(String imei, Long id);
	LaoDMPCardGroupDto selectByGroupId(Long id);
	void delCardGroup(Long id);
	Integer selectDeviceNum(Long id);
	
}
