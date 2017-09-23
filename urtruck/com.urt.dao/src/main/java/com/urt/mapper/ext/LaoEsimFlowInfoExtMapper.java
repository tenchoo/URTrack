package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimDetail;
import com.urt.po.LaoEsimFlowInfo;

public interface LaoEsimFlowInfoExtMapper {

	List<LaoEsimFlowInfo> getAccountInfo(Long lenovoId);

	void updateSurplusData(LaoEsimDetail esimDetail);

	LaoEsimFlowInfo getFlowInfo(@Param("lenovoId")String lenovoId, @Param("operator")String operator);

}