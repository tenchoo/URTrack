package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoIccidAssignBatch;

public interface LaoIccidAssignBatchExtMapper {
 
	 List<LaoIccidAssignBatch> selectByPage(Page<LaoIccidAssignBatch> iccidBatchExtpage);

	LaoIccidAssignBatch selectByPrimaryKey(Long batchId);

	int insertSelective(LaoIccidAssignBatch po);

	int insertAssignCard(LaoIccidAssignBatch po);
	
}