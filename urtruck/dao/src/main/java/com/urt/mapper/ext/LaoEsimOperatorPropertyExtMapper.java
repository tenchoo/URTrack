package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimOperatorProperty;


public interface LaoEsimOperatorPropertyExtMapper {

	LaoEsimOperatorProperty getProperty(@Param("operator")String operator);
	
}