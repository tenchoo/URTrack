package com.urt.interfaces.report;

import java.util.List;

import com.urt.dto.report.LaoReptInfoDto;


public interface ReportFormService {

    LaoReptInfoDto selectByTradeTypeCode(Long reptId);

    List<LaoReptInfoDto> selectCodeAndName();

	LaoReptInfoDto selectByType(int typeCode);
}
