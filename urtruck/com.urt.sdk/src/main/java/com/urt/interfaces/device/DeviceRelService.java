package com.urt.interfaces.device;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.Goods.LaoUserDto;

public interface DeviceRelService {

	LaoDeviceRelDto selectByUserIdAndidType(Map<String,Object> map);
    
    int batchInsert(List<LaoDeviceRelDto> list);

	Map<String, Object> selectByCondition(LaoDeviceRelDto dto, int pageNo,
			int pageSize);
	
	List<LaoSsStaticDto> getIdTypeByDeviceRel();
	
	LaoUserDto selectByIccid(String iccid);
	
    int insertSelective(LaoDeviceRelDto record);
    
    int deleteByPrimaryKey(Long relId);
    
    int updateByPrimaryKeySelective(LaoDeviceRelDto record);
    
    LaoDeviceRelDto selectByPrimaryKey(Long relId);

}
