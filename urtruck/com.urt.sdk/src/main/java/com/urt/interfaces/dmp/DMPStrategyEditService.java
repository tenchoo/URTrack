package com.urt.interfaces.dmp;

import java.util.List;
import java.util.Map;

import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.dto.dmp.LaoDMPOperationDto;
import com.urt.dto.dmp.LaoDMPStrategyDto;
import com.urt.dto.dmp.LaoDMPStrategyEditDto;


public interface DMPStrategyEditService {

	Map<String, Object> queryPage(LaoDMPStrategyEditDto schemeDto, int pageNo,int pageSize);

	int delStrategyEdit(Long id);

	int blockUp(Long id);

	int startUsing(Long id);

	List<LaoDMPGroupDto> getGroupsByCustId(Long custId);

	List<LaoDMPStrategyDto> getStrategys();

	List<LaoDMPOperationDto> getOperations();

	boolean saveScheme(LaoDMPStrategyEditDto schemeDto);

	LaoDMPStrategyEditDto getSchemeDetail(Long schemeId, Long custId);

	List<LaoDMPGroupDto> getGroupsByScheme(Long id);

	List<LaoDMPStrategyDto> getStrategis(Long id);

	List<LaoDMPOperationDto> getOperations(Long id, Long strategyId);

	boolean delStrategyRelative(Long custId, Long id);

	boolean stopScheme(Long custId, Long id);
    

}
