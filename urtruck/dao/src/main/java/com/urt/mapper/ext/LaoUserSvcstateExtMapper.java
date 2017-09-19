package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUser;


public interface LaoUserSvcstateExtMapper {
	List<Map<String,Object>> queryCardCycle(LaoUser laoUserPo);
}