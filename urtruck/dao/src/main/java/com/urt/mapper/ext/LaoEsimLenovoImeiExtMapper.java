package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface LaoEsimLenovoImeiExtMapper {

	List<Map<String, String>> getImeiInfo(String lenovoId);
	
	List<Map<String,String>> getlenovoInfo(String lenovoId);
	
	List<Map<String, Object>> getImeiFlowInfo(String lenovoId);

	int getCount(@Param("lenovoId")String lenovoId, @Param("imei")String imei);
	
	int deletesLenovoId(@Param("lenovoId")String lenovoId, @Param("imei")String imei);

	List<String> getImeisByLenovoId(@Param("lenovoId")String lenovoId);
    
}