/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.interfaces.authority;

import java.util.List;
import java.util.Map;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsStaticDto;




public interface  TagService{

	public String translateByTypeId(String typeId, String dataId);
	
	public String translateBySql(String bindSql, String translateValue);
	
	public String getTranslate(String sql, String value);
	
	public List<Map> getOptionsByTypeId(String typeId, String dataId,String custId);
	
	public List<Map> getOptionsBySql(String bindSql, String sqlCond);
	 
	public List<Map> getOptions(String sql);
	
	public List<Map> queryProductTypeByCustId(String custId);
	
	public List<Map<String,Object>> queryProductTypeVAL1();
	
	public List<Map<String,Object>> queryProductVersionVAL2(String pid);
	
	public List<Map> queryPoolsByCustId(String custId);
	
	public List<Map> queryProductVersionByPid(String pid,String custId);
	
	LaoSsStaticDto getStaticByCustIdCode(String staticCode,String custId);
	
	Map getIccidLibByIccid(String iccid);
	
	List<Map<String, Object>> queryProductTypeVAL1TSP(String custId);

	LaoSsStaticDto getStaticByCustIdCodeTSP(String staticCode, String custId);
}
