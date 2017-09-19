package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoReptInfoPo;

public interface LaoReptInfoPoExtMapper {
   
    LaoReptInfoPo selectByTradeTypeCode(Long reptId);
    
    List<LaoReptInfoPo> selectCodeAndName();

	LaoReptInfoPo selectByType(int typeCode);

}