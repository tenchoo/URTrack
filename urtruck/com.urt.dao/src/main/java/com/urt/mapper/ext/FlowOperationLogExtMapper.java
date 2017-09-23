package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;

public interface FlowOperationLogExtMapper {
	int updateResponseparm(@Param("responseparm")String responseparm,@Param("floworderid")String floworderid);
}
