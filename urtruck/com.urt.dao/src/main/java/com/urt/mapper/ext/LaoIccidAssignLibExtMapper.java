package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoIccidAssignLibPo;

public interface LaoIccidAssignLibExtMapper {

	void insertSelective(List<LaoIccidAssignLibPo> list);
	
	List<LaoIccidAssignLibPo> selectDetailByPage(Page<LaoIccidAssignLibPo> iccidLibExtpage);
	
	List<String> selectIccidsByBatchId(String batchId);
}
