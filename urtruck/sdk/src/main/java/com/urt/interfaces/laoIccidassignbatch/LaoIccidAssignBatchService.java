package com.urt.interfaces.laoIccidassignbatch;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoIccidAssignBatchDto;
import com.urt.dto.LaoIccidAssignLibDto;

public interface LaoIccidAssignBatchService {

	Map<String, Object> selectByPage(LaoIccidAssignBatchDto dto, int pageNo, int pageSize);
	
	LaoIccidAssignBatchDto selectByBatchId(Long batchId);
	
	Map<String, String> selectOneDetailByBatchId(String batchId);
		
	int saveBatchAssign(LaoIccidAssignBatchDto dto);

	List<Map<String, Object>> selectDetailByBatchId(String batchId);

	int insertAssignCard(LaoIccidAssignBatchDto dto);

	int toAssignCard(LaoIccidAssignLibDto dto);

	Map<String, Object> selectDetailByPage(LaoIccidAssignLibDto dto, int pageNo, int pageSize);

}
