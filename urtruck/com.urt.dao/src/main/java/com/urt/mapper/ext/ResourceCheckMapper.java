package com.urt.mapper.ext;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface ResourceCheckMapper {

	int queryServer(@Param("userId")Integer userId,@Param("custid")Integer custid, @Param("resourceId")Integer resourceId);
}
