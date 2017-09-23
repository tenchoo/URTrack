package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.Operators;

public interface OperatorsExtMapper {
	
	List<Operators> findOperators();
	List<Operators> selOperatorsByName(@Param("operatorsName")String operatorsName);
	String queryOperatorName(String operators);
}